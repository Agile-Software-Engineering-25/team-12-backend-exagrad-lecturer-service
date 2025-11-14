package com.ase.lecturerservice.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ase.lecturerservice.entities.FeedbackDocument;

public interface FeedbackDocumentRepository extends JpaRepository<FeedbackDocument, String> {
  List<FeedbackDocument> findByLecturerId(String lecturerId);

  List<FeedbackDocument> findByFeedbackId(String feedbackId);
}
