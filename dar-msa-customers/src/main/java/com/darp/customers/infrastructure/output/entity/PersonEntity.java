package com.darp.customers.infrastructure.output.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "public", value = "persons")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class PersonEntity {
  @Id private String id;

  @Column("full_name")
  private String fullName;

  private String gender;

  private Integer age;

  private String address;

  @Column("phone_number")
  private String phoneNumber;
}
