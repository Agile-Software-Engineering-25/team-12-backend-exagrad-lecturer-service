package com.ase.lecturerservice.entities.user;

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
}
