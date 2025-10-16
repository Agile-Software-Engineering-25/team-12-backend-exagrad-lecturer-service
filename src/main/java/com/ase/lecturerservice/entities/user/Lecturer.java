package com.ase.lecturerservice.entities.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Lecturer extends UserEntity {

  private final UserType type = UserType.LECTURER;
}
