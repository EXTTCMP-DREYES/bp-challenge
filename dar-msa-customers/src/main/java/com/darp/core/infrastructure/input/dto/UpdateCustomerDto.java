package com.darp.core.infrastructure.input.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.ToString;

public record UpdateCustomerDto(
    @NotNull @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ' ]{1,100}$") String fullName,
    @NotNull @Pattern(regexp = "[M|F]", message = "Gender should be 'M' or 'F'.") String gender,
    @NotNull
        @Pattern(
            regexp = "^(?:[1-9]|[1-9][0-9]|1[01][0-9]|120)$",
            message = "Age should be between 1 and 120.")
        String age,
    @NotNull @Pattern(regexp = "^[\\p{L}0-9\\s.,#\\-]{1,200}$") String address,
    @NotNull @Pattern(regexp = "^\\+?\\d{1,3}?[-.\\s]?\\d{1,4}([-.\\s]?\\d{2,4}){1,4}$")
        String phoneNumber,
    @NotNull
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$")
        @ToString.Exclude
        String password) {}
