package com.darp.core.domain.model.customer;

import com.darp.core.domain.model.person.Person;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Accessors(fluent = true)
@ToString
public class Customer extends Person {
  @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$")
  private String password;

  private Status status;
}
