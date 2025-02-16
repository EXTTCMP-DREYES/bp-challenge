package com.darp.core.domain.model;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Customer {
  @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
  private String id;

  @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ' ]{1,100}$")
  private String fullName;

  @Pattern(regexp = "[M|F]")
  private String gender;

  private Integer age;

  @Size(min = 1, max = 200)
  private String address;

  @Pattern(regexp = "^\\+?\\d{1,3}?[-.\\s]?\\d{1,4}([-.\\s]?\\d{2,4}){1,4}$")
  private String phoneNumber;

  private String status;
}
