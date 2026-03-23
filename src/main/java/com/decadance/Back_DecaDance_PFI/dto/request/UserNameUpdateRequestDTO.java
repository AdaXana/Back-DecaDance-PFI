package com.decadance.Back_DecaDance_PFI.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserNameUpdateRequestDTO(
    @NotBlank(message = "El nombre de usuario es obligatorio")
    String username
) {

}