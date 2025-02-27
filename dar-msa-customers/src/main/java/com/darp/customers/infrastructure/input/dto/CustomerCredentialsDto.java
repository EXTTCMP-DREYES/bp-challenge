package com.darp.customers.infrastructure.input.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.ToString;

public record CustomerCredentialsDto(
    @NotNull @Pattern(regexp = "^\\d{10,13}$") String identityNumber,
    @NotNull
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$")
        @ToString.Exclude
        String password) {}
