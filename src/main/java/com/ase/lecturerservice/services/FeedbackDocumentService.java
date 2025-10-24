package com.ase.lecturerservice.services;

import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.ase.lecturerservice.config.StorageProperties;
import com.ase.lecturerservice.dtos.FeedbackDocumentRequest;
import com.ase.lecturerservice.dtos.FeedbackDocumentResponse;
import com.ase.lecturerservice.entities.FeedbackDocument;
import com.ase.lecturerservice.mappers.FeedbackDocumentMapper;
import com.ase.lecturerservice.repositories.FeedbackDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackDocumentService {

  private final FeedbackDocumentRepository feedbackDocumentRepository;
  private final MinioService minioService;
  private final StorageProperties storageProperties;
  private final FileValidationService fileValidationService;
  private final FeedbackDocumentMapper feedbackDocumentMapper;

  @Transactional
  public FeedbackDocument uploadFeedbackDocument(
      MultipartFile file, FeedbackDocumentRequest metadata) throws IOException {
    // Validate file before processing
    fileValidationService.validateFile(file);

    String bucketName = storageProperties.getFeedbackDocumentsBucket();
    String sanitizedFilename =
        fileValidationService.sanitizeFileName(file.getOriginalFilename());
    String minioKey = generateMinioKey(sanitizedFilename);

    minioService.uploadFile(
        bucketName, minioKey, file.getInputStream(), file.getSize(), file.getContentType());

    FeedbackDocument doc =
        FeedbackDocument.builder()
            .feedbackId(metadata.getFeedbackId())
            .lecturerId(metadata.getLecturerId())
            .minioKey(minioKey)
            .fileName(sanitizedFilename)
            .build();

    FeedbackDocument saved = feedbackDocumentRepository.saveAndFlush(doc);

    return saved;
  }

  public List<FeedbackDocumentResponse> getDocumentsByFeedbackId(String feedbackId) {
    List<FeedbackDocument> documents = feedbackDocumentRepository.findByFeedbackId(feedbackId);
    return convertToResponseWithUrls(documents);
  }

  public List<FeedbackDocumentResponse> getDocumentsByDocumentId(UUID docId) {
    return feedbackDocumentRepository
        .findById(docId)
        .map(doc -> convertToResponseWithUrls(List.of(doc)))
        .orElse(List.of());
  }

  private List<FeedbackDocumentResponse> convertToResponseWithUrls(
      List<FeedbackDocument> documents) {
    String bucketName = storageProperties.getFeedbackDocumentsBucket();

    return documents.stream()
        .map(
            doc -> {
              String downloadUrl =
                  minioService.getFileUrl(bucketName, doc.getMinioKey());
              return feedbackDocumentMapper.toResponse(doc, downloadUrl);
            })
        .toList();
  }

  private String generateMinioKey(String originalFilename) {
    String year = String.valueOf(Year.now().getValue());
    String unique = UUID.randomUUID().toString();
    return "feedback-documents/" + year + "/" + unique + "-" + originalFilename;
  }
}
