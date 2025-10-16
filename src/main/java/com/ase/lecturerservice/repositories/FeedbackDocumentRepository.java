package com.ase.lecturerservice.repositories;

import com.ase.lecturerservice.entities.FeedbackDocument;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackDocumentRepository extends JpaRepository<FeedbackDocument, UUID> {
    List<FeedbackDocument> findByLecturerId(String lecturerId);

    List<FeedbackDocument> findByFeedbackId(String feedbackId);
}
