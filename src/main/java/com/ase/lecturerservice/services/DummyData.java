package com.ase.lecturerservice.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.ExamType;
import com.ase.lecturerservice.entities.FileReference;
import com.ase.lecturerservice.entities.Grade;
import com.ase.lecturerservice.entities.user.Lecturer;
import com.ase.lecturerservice.entities.user.Student;
import com.ase.lecturerservice.mockvalues.MockValues;

public class DummyData {
  static LocalDate date = LocalDate.of(
      MockValues.IntMocks.DATE_YEAR.getValue(),
      MockValues.IntMocks.DATE_MONTH.getValue(),
      MockValues.IntMocks.DATE_DAY.getValue());

  static List<Student> studentList = List.of(
      Student.builder().id(
          MockValues.UuidMocks.STUDENT_UUID.getValue()).matricalNumber("D725").build(),
      Student.builder().id(
          MockValues.UuidMocks.STUDENT_UUID2.getValue()).matricalNumber("D755").build(),
      Student.builder().id(
          MockValues.UuidMocks.STUDENT_UUID3.getValue()).matricalNumber("D735").build(),
      Student.builder().id(
          MockValues.UuidMocks.STUDENT_UUID4.getValue()).matricalNumber("D729").build(),
      Student.builder().id(
          MockValues.UuidMocks.STUDENT_UUID5.getValue()).matricalNumber("D726").build()
  );

  static List<FileReference> fileReferencesList = List.of(
      FileReference.builder()
          .fileUuid(UUID.randomUUID())
          .filename("dummy_file")
          .build(),
      FileReference.builder()
          .fileUuid(UUID.randomUUID())
          .filename("dummy_file2")
          .build()
  );

  public static List<Exam> EXAMS = List.of(
      Exam.builder()
          .uuid(MockValues.UuidMocks.EXAM_UUID.getValue())
          .name("Mathematics Final Exam")
          .grade(MockValues.FloatMocks.GRADE.getValue())
          .averageGrade(MockValues.FloatMocks.AVERAGE_GRADE.getValue())
          .totalPoints(MockValues.IntMocks.TOTAL_POINTS.getValue())
          .achievedPoints(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
          .examType(ExamType.PRESENTATION)
          .date(date)
          .time(MockValues.IntMocks.TIME_SECONDS.getValue())
          .allowedResources("Calculator, Formula Sheet")
          .attempt(MockValues.IntMocks.ATTEMPT.getValue())
          .etcs(MockValues.IntMocks.ETCS.getValue())
          .room("Room A101")
          .lecturer(new Lecturer())
          .module("Mathe")
          .assignedStudents(studentList)
          .build(),
      Exam.builder()
          .uuid(MockValues.UuidMocks.EXAM_UUID.getValue())
          .name("Physics Midterm")
          .grade(MockValues.FloatMocks.GRADE.getValue())
          .averageGrade(MockValues.FloatMocks.AVERAGE_GRADE.getValue())
          .totalPoints(MockValues.IntMocks.TOTAL_POINTS.getValue())
          .achievedPoints(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
          .examType(ExamType.TEST)
          .date(date)
          .time(MockValues.IntMocks.TIME_SECONDS.getValue())
          .allowedResources("None")
          .attempt(MockValues.IntMocks.ATTEMPT.getValue())
          .etcs(MockValues.IntMocks.ETCS.getValue())
          .room("Room B202")
          .lecturer(new Lecturer())
          .module("Physics")
          .assignedStudents(studentList)
          .build(),
      Exam.builder()
          .uuid(MockValues.UuidMocks.EXAM_UUID.getValue())
          .name("Computer Science Project")
          .grade(MockValues.FloatMocks.GRADE.getValue())
          .averageGrade(MockValues.FloatMocks.AVERAGE_GRADE.getValue())
          .totalPoints(MockValues.IntMocks.TOTAL_POINTS.getValue())
          .achievedPoints(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
          .examType(ExamType.TEST)
          .date(date)
          .time(MockValues.IntMocks.TIME_SECONDS.getValue())
          .allowedResources("Laptop, IDE")
          .attempt(MockValues.IntMocks.ATTEMPT.getValue())
          .etcs(MockValues.IntMocks.ETCS.getValue())
          .room("Online Submission")
          .lecturer(new Lecturer())
          .module("CS I")
          .assignedStudents(studentList)
          .build(),
      Exam.builder()
          .uuid(MockValues.UuidMocks.EXAM_UUID.getValue())
          .name("Chemistry Lab Exam")
          .grade(MockValues.FloatMocks.GRADE.getValue())
          .averageGrade(MockValues.FloatMocks.AVERAGE_GRADE.getValue())
          .totalPoints(MockValues.IntMocks.TOTAL_POINTS.getValue())
          .achievedPoints(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
          .examType(ExamType.TEST)
          .date(date)
          .time(MockValues.IntMocks.TIME_SECONDS.getValue())
          .allowedResources("Lab Equipment, Safety Manual")
          .attempt(MockValues.IntMocks.ATTEMPT.getValue())
          .etcs(MockValues.IntMocks.ETCS.getValue())
          .room("Lab C303")
          .room("Online Submission")
          .lecturer(new Lecturer())
          .module("Chemistry")
          .assignedStudents(studentList)
          .build(),
      Exam.builder()
          .uuid(MockValues.UuidMocks.EXAM_UUID.getValue())
          .name("History Essay Exam")
          .grade(MockValues.FloatMocks.GRADE.getValue())
          .averageGrade(MockValues.FloatMocks.AVERAGE_GRADE.getValue())
          .totalPoints(MockValues.IntMocks.TOTAL_POINTS.getValue())
          .achievedPoints(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
          .examType(ExamType.PRESENTATION)
          .date(date)
          .time(MockValues.IntMocks.TIME_SECONDS.getValue())
          .allowedResources("Notes, Textbook")
          .attempt(MockValues.IntMocks.ATTEMPT.getValue())
          .etcs(MockValues.IntMocks.ETCS.getValue())
          .room("Room D404")
          .lecturer(new Lecturer())
          .assignedStudents(studentList)
          .module("History I")
          .build()
  );

  public static List<Grade> GRADE = List.of(
      Grade.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID.getValue())
          .date(date)
          .lecturerUuid(UUID.randomUUID())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID.getValue())
          .submissionUuid(UUID.randomUUID())
          .examUuid(MockValues.UuidMocks.EXAM_UUID.getValue())
          .comment("Excellent work on the assignment.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
          .grade(MockValues.FloatMocks.GRADE.getValue())
          .build(),
      Grade.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID2.getValue())
          .date(date)
          .lecturerUuid(UUID.randomUUID())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID2.getValue())
          .submissionUuid(UUID.randomUUID())
          .examUuid(MockValues.UuidMocks.EXAM_UUID.getValue())
          .comment("Great effort! Check feedback in files.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
          .grade(MockValues.FloatMocks.GRADE.getValue())
          .build(),
      Grade.builder()
          .uuid(MockValues.UuidMocks.GRADE_UUID3.getValue())
          .date(date)
          .lecturerUuid(UUID.randomUUID())
          .studentUuid(MockValues.UuidMocks.STUDENT_UUID3.getValue())
          .submissionUuid(UUID.randomUUID())
          .examUuid(MockValues.UuidMocks.EXAM_UUID.getValue())
          .comment("Incomplete submission. Please review guidelines.")
          .fileReference(fileReferencesList)
          .points(MockValues.IntMocks.ACHIEVED_POINTS.getValue())
          .grade(MockValues.FloatMocks.GRADE.getValue())
          .build()
  );
}
