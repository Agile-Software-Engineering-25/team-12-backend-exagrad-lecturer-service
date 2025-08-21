package com.ase.userservice.entities;

import com.ase.userservice.entities.user.Lecturer;
import com.ase.userservice.entities.user.Student;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Exam {
  @Id
  @UuidGenerator
  protected UUID id;

  @Column(name = "name")
  protected String name;

  @Column(name = "grade")
  protected int grade;

  @Column(name = "average_grade")
  protected int averageGrade;

  @Column(name = "total_points")
  protected int totalPoints;

  @Column(name = "achieved_points")
  protected int achievedPoints;

  @Column(name = "exam_type")
  protected String examType;

  @Column(name = "date")
  protected LocalDate date;

  @Column(name = "time")
  protected int time;

  @Column(name = "semster")
  protected String semester;

  @Column(name = "allowed_resources")
  protected String allowedResources;

  @Column(name = "attempt")
  protected int attempt;

  @Column(name = "etcs")
  protected int etcs;

  @Column(name = "room")
  protected String room;

  @Column(name = "module")
  protected String module;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "lecturer_id")
  protected Lecturer lecturer;

  @ManyToMany(mappedBy = "assignedExams", fetch = FetchType.LAZY)
  protected List<Student> assignedStudents;
}
