package com.darp.customers.domain.model.person;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@ToString
public class Person {
  @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
  private String id;

  @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ' ]{1,100}$")
  private String fullName;

  private PersonGender gender;

  @Min(1)
  @Max(120)
  private Integer age;

  @Size(min = 1, max = 200)
  private String address;

  @Pattern(regexp = "^\\+?\\d{1,3}?[-.\\s]?\\d{1,4}([-.\\s]?\\d{2,4}){1,4}$")
  private String phoneNumber;
}
