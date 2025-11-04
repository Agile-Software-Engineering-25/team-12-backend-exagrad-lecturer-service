package com.ase.lecturerservice.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import com.ase.lecturerservice.dtos.FeedbackRequest;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.ExamType;
import com.ase.lecturerservice.entities.FileReference;
import com.ase.lecturerservice.entities.PublishStatus;
import com.ase.lecturerservice.entities.Submission;
import com.ase.lecturerservice.entities.user.Student;
import com.ase.lecturerservice.mockvalues.MockValues;

public class DummyData {
  public static List<Submission> SUBMISSIONS =
      List.of(
          Submission.builder()
              .studentUuid(MockValues.UuidMocks.STUDENT_UUID.getValue())
              .examUuid(MockValues.UuidMocks.EXAM_UUID.getValue())
              .submissionDate("2025-09-01T10:30:00Z")
              .fileUpload(
                  List.of(
                      FileReference.builder()
                          .fileUuid(UUID.randomUUID().toString())
                          .fileName("submission1.pdf")
                          .build()))
              .build(),
          Submission.builder()
              .studentUuid(MockValues.UuidMocks.STUDENT_UUID2.getValue())
              .examUuid(MockValues.UuidMocks.EXAM_UUID.getValue())
              .submissionDate("2025-09-01T11:10:00Z")
              .fileUpload(
                  List.of(
                      FileReference.builder()
                          .fileUuid(UUID.randomUUID().toString())
                          .fileName("submission2.pdf")
                          .build()))
              .build(),
          Submission.builder()
              .studentUuid(MockValues.UuidMocks.STUDENT_UUID.getValue())
              .examUuid(MockValues.UuidMocks.EXAM_UUID2.getValue())
              .submissionDate("2025-09-02T09:15:00Z")
              .fileUpload(
                  List.of(
                      FileReference.builder()
                          .fileUuid(UUID.randomUUID().toString())
                          .fileName("physics_midterm_attempt1.zip")
                          .build()))
              .build(),
          Submission.builder()
              .studentUuid(MockValues.UuidMocks.STUDENT_UUID3.getValue())
              .examUuid(MockValues.UuidMocks.EXAM_UUID.getValue())
              .submissionDate("2025-09-03T14:45:00Z")
              .fileUpload(
                  List.of(
                      FileReference.builder()
                          .fileUuid(UUID.randomUUID().toString())
                          .fileName("software_project_demo.mp4")
                          .build()))
              .build(),
          Submission.builder()
              .studentUuid(MockValues.UuidMocks.STUDENT_UUID4.getValue())
              .examUuid(MockValues.UuidMocks.EXAM_UUID.getValue())
              .submissionDate("2025-09-04T08:05:00Z")
              .fileUpload(
                  List.of(
                      FileReference.builder()
                          .fileUuid(UUID.randomUUID().toString())
                          .fileName("chemistry_lab_report.docx")
                          .build()))
              .build());
  static LocalDate date =
      LocalDate.of(
          MockValues.IntMocks.DATE_YEAR.getValue(),
          MockValues.IntMocks.DATE_MONTH.getValue(),
          MockValues.IntMocks.DATE_DAY.getValue());
  public static List<FeedbackRequest> Feedbacks =
      List.of(
          FeedbackRequest.builder()
              .gradedAt(date)
              .lecturerUuid(UUID.randomUUID().toString())
              .studentUuid(MockValues.UuidMocks.STUDENT_UUID.getValue())
              .submissionUuid(UUID.randomUUID().toString())
              .examUuid(MockValues.UuidMocks.EXAM_UUID.getValue())
              .comment("Excellent work on the assignment.")
              .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
              .grade(MockValues.FloatMocks.GRADE.getValue())
              .build(),
          FeedbackRequest.builder()
              .gradedAt(date)
              .lecturerUuid(UUID.randomUUID().toString())
              .studentUuid(MockValues.UuidMocks.STUDENT_UUID2.getValue())
              .submissionUuid(UUID.randomUUID().toString())
              .examUuid(MockValues.UuidMocks.EXAM_UUID.getValue())
              .comment("Great effort! Check feedback in files.")
              .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
              .grade(MockValues.FloatMocks.GRADE.getValue())
              .build(),
          FeedbackRequest.builder()
              .gradedAt(date)
              .lecturerUuid(UUID.randomUUID().toString())
              .studentUuid(MockValues.UuidMocks.STUDENT_UUID3.getValue())
              .submissionUuid(UUID.randomUUID().toString())
              .examUuid(MockValues.UuidMocks.EXAM_UUID.getValue())
              .comment("Incomplete submission. Please review guidelines.")
              .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
              .grade(MockValues.FloatMocks.GRADE.getValue())
              .build(),
          FeedbackRequest.builder()
              .gradedAt(date)
              .lecturerUuid(UUID.randomUUID().toString())
              .studentUuid(MockValues.UuidMocks.STUDENT_UUID.getValue())
              .submissionUuid(UUID.randomUUID().toString())
              .examUuid(MockValues.UuidMocks.EXAM_UUID6.getValue())
              .comment("Great effort! Check feedback in files.")
              .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
              .grade(MockValues.FloatMocks.GRADE.getValue())
              .build(),
          FeedbackRequest.builder()
              .gradedAt(date)
              .lecturerUuid(UUID.randomUUID().toString())
              .studentUuid(MockValues.UuidMocks.STUDENT_UUID2.getValue())
              .submissionUuid(UUID.randomUUID().toString())
              .examUuid(MockValues.UuidMocks.EXAM_UUID6.getValue())
              .comment("Great effort! Check feedback in files.")
              .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
              .grade(MockValues.FloatMocks.GRADE.getValue())
              .build(),
          FeedbackRequest.builder()
              .gradedAt(date)
              .lecturerUuid(UUID.randomUUID().toString())
              .studentUuid(MockValues.UuidMocks.STUDENT_UUID.getValue())
              .submissionUuid(UUID.randomUUID().toString())
              .examUuid(MockValues.UuidMocks.EXAM_UUID2.getValue())
              .comment("Great effort! Check feedback in files.")
              .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
              .grade(MockValues.FloatMocks.GRADE.getValue())
              .build(),
          FeedbackRequest.builder()
              .gradedAt(date)
              .lecturerUuid(UUID.randomUUID().toString())
              .studentUuid(MockValues.UuidMocks.STUDENT_UUID2.getValue())
              .submissionUuid(UUID.randomUUID().toString())
              .examUuid(MockValues.UuidMocks.EXAM_UUID2.getValue())
              .comment("Great effort! Check feedback in files.")
              .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
              .grade(MockValues.FloatMocks.GRADE.getValue())
              .build(),
          FeedbackRequest.builder()
              .gradedAt(date)
              .lecturerUuid(UUID.randomUUID().toString())
              .studentUuid(MockValues.UuidMocks.STUDENT_UUID.getValue())
              .submissionUuid(UUID.randomUUID().toString())
              .examUuid(MockValues.UuidMocks.EXAM_UUID4.getValue())
              .comment("Great effort! Check feedback in files.")
              .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
              .grade(MockValues.FloatMocks.GRADE.getValue())
              .build());
  static List<Student> studentList =
      List.of(
          Student.builder()
              .uuid(MockValues.UuidMocks.STUDENT_UUID.getValue())
              .matriculationNumber("D725")
              .build(),
          Student.builder()
              .uuid(MockValues.UuidMocks.STUDENT_UUID2.getValue())
              .matriculationNumber("D755")
              .build(),
          Student.builder()
              .uuid(MockValues.UuidMocks.STUDENT_UUID3.getValue())
              .matriculationNumber("D735")
              .build(),
          Student.builder()
              .uuid(MockValues.UuidMocks.STUDENT_UUID4.getValue())
              .matriculationNumber("D729")
              .build(),
          Student.builder()
              .uuid(MockValues.UuidMocks.STUDENT_UUID5.getValue())
              .matriculationNumber("D726")
              .build());
  static List<Student> studentList2 =
      List.of(
          Student.builder()
              .uuid(MockValues.UuidMocks.STUDENT_UUID.getValue())
              .matriculationNumber("D725")
              .build(),
          Student.builder()
              .uuid(MockValues.UuidMocks.STUDENT_UUID2.getValue())
              .matriculationNumber("D755")
              .build());
  public static List<Exam> EXAMS =
      List.of(
          Exam.builder()
              .uuid(MockValues.UuidMocks.EXAM_UUID.getValue())
              .name("Mathematics Final Exam")
              .totalPoints(MockValues.IntMocks.TOTAL_POINTS.getValue())
              .examType(ExamType.PRESENTATION)
              .date(date)
              .time(MockValues.IntMocks.TIME_SECONDS.getValue())
              .allowedResources("Calculator, Formula Sheet")
              .attempt(MockValues.IntMocks.ATTEMPT.getValue())
              .etcs(MockValues.IntMocks.ETCS.getValue())
              .room("Room A101")
              .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
              .module("Mathe")
              .assignedStudents(studentList)
              .fileUploadRequired(false)
              .build(),
          Exam.builder()
              .uuid(MockValues.UuidMocks.EXAM_UUID2.getValue())
              .name("Physics Midterm")
              .totalPoints(MockValues.IntMocks.TOTAL_POINTS.getValue())
              .examType(ExamType.EXAM)
              .date(date)
              .time(MockValues.IntMocks.TIME_SECONDS.getValue())
              .allowedResources("None")
              .attempt(MockValues.IntMocks.ATTEMPT.getValue())
              .etcs(MockValues.IntMocks.ETCS.getValue())
              .room("Room B202")
              .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID.getValue())
              .module("Physics")
              .assignedStudents(studentList2)
              .fileUploadRequired(false)
              .build(),
          Exam.builder()
              .uuid(MockValues.UuidMocks.EXAM_UUID3.getValue())
              .name("Computer Science Project")
              .totalPoints(MockValues.IntMocks.TOTAL_POINTS.getValue())
              .examType(ExamType.OTHERS)
              .date(date)
              .time(MockValues.IntMocks.TIME_SECONDS.getValue())
              .allowedResources("Laptop, IDE")
              .attempt(MockValues.IntMocks.ATTEMPT.getValue())
              .etcs(MockValues.IntMocks.ETCS.getValue())
              .room("Online Submission")
              .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID2.getValue())
              .module("CS I")
              .assignedStudents(studentList)
              .fileUploadRequired(true)
              .build(),
          Exam.builder()
              .uuid(MockValues.UuidMocks.EXAM_UUID4.getValue())
              .name("Chemistry Lab Exam")
              .totalPoints(MockValues.IntMocks.TOTAL_POINTS.getValue())
              .examType(ExamType.ORAL)
              .date(date)
              .time(MockValues.IntMocks.TIME_SECONDS.getValue())
              .allowedResources("Lab Equipment, Safety Manual")
              .attempt(MockValues.IntMocks.ATTEMPT.getValue())
              .etcs(MockValues.IntMocks.ETCS.getValue())
              .room("Online Submission")
              .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID2.getValue())
              .module("Chemistry")
              .assignedStudents(studentList2)
              .fileUploadRequired(false)
              .build(),
          Exam.builder()
              .uuid(MockValues.UuidMocks.EXAM_UUID6.getValue())
              .name("Software Development Project")
              .totalPoints(MockValues.IntMocks.TOTAL_POINTS.getValue())
              .examType(ExamType.PRESENTATION)
              .date(date)
              .time(MockValues.IntMocks.TIME_SECONDS.getValue())
              .allowedResources("Notes, Textbook")
              .attempt(MockValues.IntMocks.ATTEMPT.getValue())
              .etcs(MockValues.IntMocks.ETCS.getValue())
              .room("Room C303")
              .lecturerUuid(MockValues.UuidMocks.LECTURER_UUID2.getValue())
              .assignedStudents(studentList2)
              .fileUploadRequired(true)
              .module("History I")
              .build());
  static List<FileReference> fileReferencesList =
      List.of(
          FileReference.builder()
              .fileUuid(UUID.randomUUID().toString())
              .fileName("dummy_file")
              .build(),
          FileReference.builder()
              .fileUuid(UUID.randomUUID().toString())
              .fileName("dummy_file2")
              .build());
}
