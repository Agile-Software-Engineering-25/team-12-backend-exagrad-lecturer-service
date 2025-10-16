package com.ase.lecturerservice.repositories;

import com.ase.lecturerservice.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {}
