package com.ase.lecturerservice.entities.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class UserEntity {
  @Column(name = "email", insertable = false, updatable = false)
  protected String email;

  @Id
  @UuidGenerator
  private UUID id;

  @Column(name = "first_name", nullable = false)
  @JsonProperty("first_name")
  private String firstName;

  @Column(name = "last_name", nullable = false)
  @JsonProperty("last_name")
  private String lastName;

  @Column(name = "type")
  @JsonProperty("type")
  private UserType type;
}
