package com.ase.lecturerservice.entities;

import java.time.LocalDate;
import java.util.List;
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
  private String uuid;

  private String name;

  private int totalPoints;

  private ExamType examType;

  private LocalDate date;

  private int time;

  private String allowedResources;

  private int attempt;

  private int etcs;

  private String room;

  private String module;

  private Lecturer lecturer;

  private List<Student> assignedStudents;
}
