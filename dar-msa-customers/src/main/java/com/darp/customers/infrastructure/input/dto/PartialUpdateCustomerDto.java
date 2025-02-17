package com.darp.customers.infrastructure.input.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import lombok.ToString;

public record PartialUpdateCustomerDto(
    @Nullable @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ' ]{1,100}$") String fullName,
    @Nullable @Pattern(regexp = "[M|F]", message = "Gender should be 'M' or 'F'.") String gender,
    @Nullable
        @Pattern(
            regexp = "^(?:[1-9]|[1-9][0-9]|1[01][0-9]|120)$",
            message = "Age should be between 1 and 120.")
        String age,
    @Nullable @Pattern(regexp = "^[\\p{L}0-9\\s.,#\\-]{1,200}$") String address,
    @Nullable @Pattern(regexp = "^\\+?\\d{1,3}?[-.\\s]?\\d{1,4}([-.\\s]?\\d{2,4}){1,4}$")
        String phoneNumber,
    @Nullable
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$")
        @ToString.Exclude
        String password) {}
