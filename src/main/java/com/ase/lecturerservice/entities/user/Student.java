package com.ase.lecturerservice.entities.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class Student extends UserEntity {
  protected final String matricalNumber;
  protected final UserType type = UserType.STUDENT;
}
