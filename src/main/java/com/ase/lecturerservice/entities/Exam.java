package com.ase.lecturerservice.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import com.ase.lecturerservice.entities.user.Lecturer;
import com.ase.lecturerservice.entities.user.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exam {
  protected UUID id;

  protected String name;

  protected int grade;

  protected int averageGrade;

  protected int totalPoints;

  protected int achievedPoints;

  protected String examType;

  protected LocalDate date;

  protected int time;

  protected String allowedResources;

  protected int attempt;

  protected int etcs;

  protected String room;

  protected String module;

  protected Lecturer lecturer;

  protected List<Student> assignedStudents;
}
