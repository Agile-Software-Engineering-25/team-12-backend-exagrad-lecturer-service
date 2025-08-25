package com.ase.lecturerservice.entities.user;

import com.ase.lecturerservice.entities.Exam;
import jakarta.persistence.ManyToMany;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Student extends UserEntity {
  protected final UserType type = UserType.STUDENT;

  @ManyToMany
  protected List<Exam> assignedExams;
}
