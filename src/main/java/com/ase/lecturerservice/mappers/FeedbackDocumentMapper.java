package com.ase.lecturerservice.mappers;

import java.time.ZoneId;
import org.springframework.stereotype.Component;
import com.ase.lecturerservice.dtos.FeedbackDocumentResponse;
import com.ase.lecturerservice.entities.FeedbackDocument;
import com.ase.lecturerservice.entities.FileReference;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedbackDocumentMapper {

  private final ZoneId appZoneId;

  public FeedbackDocumentResponse toResponse(FeedbackDocument entity, String downloadUrl) {
    return FeedbackDocumentResponse.builder()
        .id(entity.getId())
        .feedbackId(entity.getFeedbackId())
        .lecturerId(entity.getLecturerId())
        .uploadDate(entity.getUploadDate().atZone(appZoneId))
        .downloadUrl(downloadUrl)
        .fileName(entity.getFileName())
        .build();
  }

  public FileReference toReference(FeedbackDocument entity) {
    return FileReference.builder()
        .fileUuid(entity.getId())
        .fileName(entity.getFileName())
        .build();
  }
}
