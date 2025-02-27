package com.darp.customers.infrastructure.input.dto;

public record CustomerDto(
    String id,
    String identityNumber,
    String fullName,
    String gender,
    Integer age,
    String address,
    String phoneNumber,
    String status) {}
