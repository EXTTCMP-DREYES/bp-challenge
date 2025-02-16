package com.darp.core.infrastructure.output.api.dto;

public record CustomerDto(
    String id,
    String fullName,
    String gender,
    Integer age,
    String address,
    String phoneNumber,
    String status) {}
