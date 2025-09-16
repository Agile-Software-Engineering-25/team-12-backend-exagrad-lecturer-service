package com.ase.lecturerservice.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ase.lecturerservice.entities.Feedback;
import com.ase.lecturerservice.repositories.FeedbackRepository;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {
  @Mock
  private FeedbackRepository feedbackRepository;

  @InjectMocks
  private FeedbackService feedbackService;

  @Test
  void saveFeedbackShouldCallRepository() {
    Feedback feedback = Feedback.builder()
        .uuid("test-uuid")
        .comment("Test comment")
        .points(1)
        .grade(1.0f)
        .build();

    feedbackService.saveFeedback(feedback);

    verify(feedbackRepository).save(feedback);
  }

  @Test
  void saveFeedbackShouldHandleNullFeedback() {
    assertThrows(NullPointerException.class, () -> {
      feedbackService.saveFeedback(null);
    });
  }

}
