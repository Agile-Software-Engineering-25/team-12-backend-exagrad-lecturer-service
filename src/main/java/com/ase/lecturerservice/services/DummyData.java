package com.ase.lecturerservice.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import com.ase.lecturerservice.entities.Feedback;
import com.ase.lecturerservice.entities.FileReference;
import com.ase.lecturerservice.entities.PublishStatus;
import com.ase.lecturerservice.mockvalues.MockValues;

public class DummyData {
  static LocalDate date = LocalDate.of(
      MockValues.IntMocks.DATE_YEAR.getValue(),
      MockValues.IntMocks.DATE_MONTH.getValue(),
      MockValues.IntMocks.DATE_DAY.getValue());
  static List<FileReference> fileReferencesList = List.of(
      FileReference.builder()
          .fileUuid(UUID.randomUUID().toString())
          .filename("dummy_file")
          .build(),
      FileReference.builder()
          .fileUuid(UUID.randomUUID().toString())
          .filename("dummy_file2")
          .build()
  );

  public static List<Feedback> Feedbacks = List.of(
      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID1.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID1.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID1.getValue())
          .comment("Excellent work on the assignment.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue()) // 95
          .grade(MockValues.FloatMocks.GRADE.getValue()) // 1.3
          .publishStatus(PublishStatus.PUBLISHED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID2.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID2.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID2.getValue())
          .comment("Good effort but needs better structuring.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS3.getValue()) // 80
          .grade(MockValues.FloatMocks.GRADE3.getValue()) // 2.3
          .publishStatus(PublishStatus.UNPUBLISHED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID3.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID3.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID3.getValue())
          .comment("Incomplete submission. Please review the guidelines.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS2.getValue()) // 60
          .grade(MockValues.FloatMocks.GRADE2.getValue()) // 3.7
          .publishStatus(PublishStatus.REJECTED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID4.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID1.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID4.getValue())
          .comment("Well done! Small issues with formatting.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS4.getValue()) // 70
          .grade(MockValues.FloatMocks.GRADE4.getValue()) // 3.0
          .publishStatus(PublishStatus.APPROVED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID5.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID2.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID5.getValue())
          .comment("Strong report and clear reasoning.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
          .grade(MockValues.FloatMocks.GRADE.getValue())
          .publishStatus(PublishStatus.APPROVED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID6.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID3.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID6.getValue())
          .comment("Good start, but missing conclusion section.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS2.getValue())
          .grade(MockValues.FloatMocks.GRADE2.getValue())
          .publishStatus(PublishStatus.UNPUBLISHED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID7.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID1.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID7.getValue())
          .comment("Creative presentation and thorough research.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS3.getValue())
          .grade(MockValues.FloatMocks.GRADE3.getValue())
          .publishStatus(PublishStatus.PUBLISHED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID8.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID2.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID8.getValue())
          .comment("Good understanding but lacks detail in analysis.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS4.getValue())
          .grade(MockValues.FloatMocks.GRADE4.getValue())
          .publishStatus(PublishStatus.REJECTED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID9.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID3.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID9.getValue())
          .comment("Outstanding presentation! Very clear explanations.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
          .grade(MockValues.FloatMocks.GRADE.getValue())
          .publishStatus(PublishStatus.APPROVED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID10.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID1.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID10.getValue())
          .comment("Good analysis but missing key results.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS3.getValue())
          .grade(MockValues.FloatMocks.GRADE3.getValue())
          .publishStatus(PublishStatus.UNPUBLISHED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID11.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID2.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID11.getValue())
          .comment("Great improvement since the last submission.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
          .grade(MockValues.FloatMocks.GRADE.getValue())
          .publishStatus(PublishStatus.PUBLISHED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID12.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID3.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID12.getValue())
          .comment("Thorough explanation and logical reasoning.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS4.getValue())
          .grade(MockValues.FloatMocks.GRADE4.getValue())
          .publishStatus(PublishStatus.APPROVED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID13.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID1.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID13.getValue())
          .comment("Impressive technical implementation.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
          .grade(MockValues.FloatMocks.GRADE.getValue())
          .publishStatus(PublishStatus.APPROVED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID14.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID2.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID14.getValue())
          .comment("Missed key points in the discussion, but overall good.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS3.getValue())
          .grade(MockValues.FloatMocks.GRADE3.getValue())
          .publishStatus(PublishStatus.UNPUBLISHED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID15.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID3.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID15.getValue())
          .comment("Good problem-solving, minor calculation error.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS4.getValue())
          .grade(MockValues.FloatMocks.GRADE4.getValue())
          .publishStatus(PublishStatus.REJECTED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID16.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID1.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID16.getValue())
          .comment("Excellent grasp of the topic.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
          .grade(MockValues.FloatMocks.GRADE.getValue())
          .publishStatus(PublishStatus.PUBLISHED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID17.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID2.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID17.getValue())
          .comment("Good teamwork and presentation clarity.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS3.getValue())
          .grade(MockValues.FloatMocks.GRADE3.getValue())
          .publishStatus(PublishStatus.APPROVED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID18.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID3.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID18.getValue())
          .comment("Detailed explanations and good examples.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
          .grade(MockValues.FloatMocks.GRADE.getValue())
          .publishStatus(PublishStatus.APPROVED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID19.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID2.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID19.getValue())
          .comment("Lacked citations but content was strong.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS4.getValue())
          .grade(MockValues.FloatMocks.GRADE4.getValue())
          .publishStatus(PublishStatus.UNPUBLISHED)
          .build(),

      Feedback.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID20.getValue())
          .gradedAt(date)
          .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID1.getValue())
          .submissionUuid(UUID.randomUUID().toString())
          .examUuid(MockValues.UuidMocks.EXAM_UUID20.getValue())
          .comment("Excellent final presentation. Top quality work.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
          .grade(MockValues.FloatMocks.GRADE.getValue())
          .publishStatus(PublishStatus.APPROVED)
          .build()
  );
}
