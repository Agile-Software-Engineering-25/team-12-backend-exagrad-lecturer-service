package com.ase.lecturerservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ase.lecturerservice.entities.Feedback;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {
  List<Feedback> findByExamUuid(String examUuid);
}
