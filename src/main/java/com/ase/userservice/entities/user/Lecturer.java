package com.ase.userservice.entities.user;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SuperBuilder
public class Lecturer extends UserEntity{

  protected final UserType type = UserType.LECTURER;
}
