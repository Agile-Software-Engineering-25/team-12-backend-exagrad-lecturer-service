package com.ase.lecturerservice.services;



import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.FileReference;
import com.ase.lecturerservice.services.MinioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.ase.lecturerservice.entities.UploadFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

private final MinioService minioService;

public void saveFiles(List<UploadFile> files) {
  for (UploadFile file : files) {
    //minioService.uploadFile(file); // ✅ korrekt!
  }
}
  /*
   * public void saveFiles(List<FileReference> fileReferences) {
   * if (fileReferences == null || fileReferences.isEmpty()) {
   * return;
   * } else {
   * for (FileReference fileRef : fileReferences) {
   * log.info("Saving file with reference: {}", fileRef.getFilename());
   * // file saving logic here
   * }
   * log.info("All files have been processed.");
   * }
   * }
   */
}
