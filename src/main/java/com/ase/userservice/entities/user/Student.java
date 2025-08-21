package com.ase.userservice.entities.user;

import com.ase.userservice.entities.Exam;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SuperBuilder
public class Student extends UserEntity{
  protected final UserType type = UserType.STUDENT;

  @ManyToMany
  protected List<Exam> assignedExams;
}
