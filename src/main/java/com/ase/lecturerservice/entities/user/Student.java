package com.ase.lecturerservice.entities.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Student extends UserEntity {

  private String matricalNumber;

  private UserType type = UserType.STUDENT;
}
