package com.ase.lecturerservice.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ase.lecturerservice.entities.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {
  List<Feedback> findByExamUuid(String examUuid);
  List<Feedback> findByStudentUuid(String studentUuid);
}
