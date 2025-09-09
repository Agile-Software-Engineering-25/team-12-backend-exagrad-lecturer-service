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
      MockValues.DATE_YEAR.getValue(),
      MockValues.DATE_MONTH.getValue(),
      MockValues.DATE_DAY.getValue());

  static List<Student> studentList = List.of(
      Student.builder().id(UUID.randomUUID()).matricalNumber("D725").build(),
      Student.builder().id(UUID.randomUUID()).matricalNumber("D755").build(),
      Student.builder().id(UUID.randomUUID()).matricalNumber("D735").build(),
      Student.builder().id(UUID.randomUUID()).matricalNumber("D729").build(),
      Student.builder().id(UUID.randomUUID()).matricalNumber("D726").build()
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
          .uuid(UUID.randomUUID())
          .name("Mathematics Final Exam")
          .grade(MockValues.GRADE.getValue())
          .averageGrade(MockValues.AVERAGE_GRADE.getValue())
          .totalPoints(MockValues.TOTAL_POINTS.getValue())
          .achievedPoints(MockValues.ACHIEVED_POINTS.getValue())
          .examType(ExamType.PRESENTATION)
          .date(date)
          .time(MockValues.TIME_SECONDS.getValue())
          .allowedResources("Calculator, Formula Sheet")
          .attempt(MockValues.ATTEMPT.getValue())
          .etcs(MockValues.ETCS.getValue())
          .room("Room A101")
          .lecturer(new Lecturer())
          .module("Mathe")
          .assignedStudents(studentList)
          .build(),
      Exam.builder()
          .uuid(UUID.randomUUID())
          .name("Physics Midterm")
          .grade(MockValues.GRADE.getValue())
          .averageGrade(MockValues.AVERAGE_GRADE.getValue())
          .totalPoints(MockValues.TOTAL_POINTS.getValue())
          .achievedPoints(MockValues.ACHIEVED_POINTS.getValue())
          .examType(ExamType.TEST)
          .date(date)
          .time(MockValues.TIME_SECONDS.getValue())
          .allowedResources("None")
          .attempt(MockValues.ATTEMPT.getValue())
          .etcs(MockValues.ETCS.getValue())
          .room("Room B202")
          .lecturer(new Lecturer())
          .module("Physics")
          .assignedStudents(studentList)
          .build(),
      Exam.builder()
          .uuid(UUID.randomUUID())
          .name("Computer Science Project")
          .grade(MockValues.GRADE.getValue())
          .averageGrade(MockValues.AVERAGE_GRADE.getValue())
          .totalPoints(MockValues.TOTAL_POINTS.getValue())
          .achievedPoints(MockValues.ACHIEVED_POINTS.getValue())
          .examType(ExamType.TEST)
          .date(date)
          .time(MockValues.TIME_SECONDS.getValue())
          .allowedResources("Laptop, IDE")
          .attempt(MockValues.ATTEMPT.getValue())
          .etcs(MockValues.ETCS.getValue())
          .room("Online Submission")
          .lecturer(new Lecturer())
          .module("CS I")
          .assignedStudents(studentList)
          .build(),
      Exam.builder()
          .uuid(UUID.randomUUID())
          .name("Chemistry Lab Exam")
          .grade(MockValues.GRADE.getValue())
          .averageGrade(MockValues.AVERAGE_GRADE.getValue())
          .totalPoints(MockValues.TOTAL_POINTS.getValue())
          .achievedPoints(MockValues.ACHIEVED_POINTS.getValue())
          .examType(ExamType.TEST)
          .date(date)
          .time(MockValues.TIME_SECONDS.getValue())
          .allowedResources("Lab Equipment, Safety Manual")
          .attempt(MockValues.ATTEMPT.getValue())
          .etcs(MockValues.ETCS.getValue())
          .room("Lab C303")
          .room("Online Submission")
          .lecturer(new Lecturer())
          .module("Chemistry")
          .assignedStudents(studentList)
          .build(),
      Exam.builder()
          .uuid(UUID.randomUUID())
          .name("History Essay Exam")
          .grade(MockValues.GRADE.getValue())
          .averageGrade(MockValues.AVERAGE_GRADE.getValue())
          .totalPoints(MockValues.TOTAL_POINTS.getValue())
          .achievedPoints(MockValues.ACHIEVED_POINTS.getValue())
          .examType(ExamType.PRESENTATION)
          .date(date)
          .time(MockValues.TIME_SECONDS.getValue())
          .allowedResources("Notes, Textbook")
          .attempt(MockValues.ATTEMPT.getValue())
          .etcs(MockValues.ETCS.getValue())
          .room("Room D404")
          .lecturer(new Lecturer())
          .assignedStudents(studentList)
          .module("History I")
          .build()
  );

  public static List<Grade> GRADE = List.of(
      Grade.builder()
          .uuid(UUID.randomUUID())
          .date(LocalDate.of(2025, 9, 1))
          .lecturerUuid(UUID.randomUUID())
          .studentUuid(UUID.randomUUID())
          .submissionUuid(UUID.randomUUID())
          .comment("Excellent work on the assignment.")
          .fileReference(fileReferencesList)
          .points(90)
          .grade(1.3f)
          .build(),
      Grade.builder()
          .uuid(UUID.randomUUID())
          .date(LocalDate.of(2025, 8, 20))
          .lecturerUuid(UUID.randomUUID())
          .studentUuid(UUID.randomUUID())
          .submissionUuid(UUID.randomUUID())
          .comment("Great effort! Check feedback in files.")
          .fileReference(fileReferencesList)
          .points(75)
          .grade(2.0f)
          .build(),
      Grade.builder()
          .uuid(UUID.randomUUID())
          .date(LocalDate.of(2025, 7, 10))
          .lecturerUuid(UUID.randomUUID())
          .studentUuid(UUID.randomUUID())
          .submissionUuid(UUID.randomUUID())
          .comment("Incomplete submission. Please review guidelines.")
          .fileReference(fileReferencesList) // Empty list
          .points(30)
          .grade(5.0f)
          .build()
  );
}
