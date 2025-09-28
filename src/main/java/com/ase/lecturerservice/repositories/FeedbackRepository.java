package com.ase.lecturerservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ase.lecturerservice.entities.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {
}
