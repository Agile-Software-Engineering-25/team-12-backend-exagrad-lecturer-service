package com.ase.lecturerservice.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import com.ase.lecturerservice.entities.Exam;
import com.ase.lecturerservice.entities.user.Lecturer;
import com.ase.lecturerservice.mockvalues.MockValues;

public class DummyData {
  static LocalDate date = LocalDate.of(
      MockValues.DATE_YEAR.getValue(),
      MockValues.DATE_MONTH.getValue(),
      MockValues.DATE_DAY.getValue());

  public static List<Exam> EXAMS = List.of(
      Exam.builder()
          .uuid(UUID.randomUUID())
          .name("Mathematics Final Exam")
          .grade(MockValues.GRADE.getValue())
          .averageGrade(MockValues.AVERAGE_GRADE.getValue())
          .totalPoints(MockValues.TOTAL_POINTS.getValue())
          .achievedPoints(MockValues.ACHIEVED_POINTS.getValue())
          .examType("Written")
          .date(date)
          .time(MockValues.TIME_SECONDS.getValue())
          .allowedResources("Calculator, Formula Sheet")
          .attempt(MockValues.ATTEMPT.getValue())
          .etcs(MockValues.ETCS.getValue())
          .room("Room A101")
          .lecturer(new Lecturer())
          .module("Mathe")
          .build(),
      Exam.builder()
          .uuid(UUID.randomUUID())
          .name("Physics Midterm")
          .grade(MockValues.GRADE.getValue())
          .averageGrade(MockValues.AVERAGE_GRADE.getValue())
          .totalPoints(MockValues.TOTAL_POINTS.getValue())
          .achievedPoints(MockValues.ACHIEVED_POINTS.getValue())
          .examType("Oral")
          .date(date)
          .time(MockValues.TIME_SECONDS.getValue())
          .allowedResources("None")
          .attempt(MockValues.ATTEMPT.getValue())
          .etcs(MockValues.ETCS.getValue())
          .room("Room B202")
          .lecturer(new Lecturer())
          .module("Physics")
          .build(),
      Exam.builder()
          .uuid(UUID.randomUUID())
          .name("Computer Science Project")
          .grade(MockValues.GRADE.getValue())
          .averageGrade(MockValues.AVERAGE_GRADE.getValue())
          .totalPoints(MockValues.TOTAL_POINTS.getValue())
          .achievedPoints(MockValues.ACHIEVED_POINTS.getValue())
          .examType("Project")
          .date(date)
          .time(MockValues.TIME_SECONDS.getValue())
          .allowedResources("Laptop, IDE")
          .attempt(MockValues.ATTEMPT.getValue())
          .etcs(MockValues.ETCS.getValue())
          .room("Online Submission")
          .lecturer(new Lecturer())
          .module("CS I")
          .build(),
      Exam.builder()
          .uuid(UUID.randomUUID())
          .name("Chemistry Lab Exam")
          .grade(MockValues.GRADE.getValue())
          .averageGrade(MockValues.AVERAGE_GRADE.getValue())
          .totalPoints(MockValues.TOTAL_POINTS.getValue())
          .achievedPoints(MockValues.ACHIEVED_POINTS.getValue())
          .examType("Practical")
          .date(date)
          .time(MockValues.TIME_SECONDS.getValue())
          .allowedResources("Lab Equipment, Safety Manual")
          .attempt(MockValues.ATTEMPT.getValue())
          .etcs(MockValues.ETCS.getValue())
          .room("Lab C303")
          .room("Online Submission")
          .lecturer(new Lecturer())
          .module("Chemistry")
          .build(),
      Exam.builder()
          .uuid(UUID.randomUUID())
          .name("History Essay Exam")
          .grade(MockValues.GRADE.getValue())
          .averageGrade(MockValues.AVERAGE_GRADE.getValue())
          .totalPoints(MockValues.TOTAL_POINTS.getValue())
          .achievedPoints(MockValues.ACHIEVED_POINTS.getValue())
          .examType("Essay")
          .date(date)
          .time(MockValues.TIME_SECONDS.getValue())
          .allowedResources("Notes, Textbook")
          .attempt(MockValues.ATTEMPT.getValue())
          .etcs(MockValues.ETCS.getValue())
          .room("Room D404")
          .lecturer(new Lecturer())
          .module("History I")
          .build()
  );
}
