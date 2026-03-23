package com.decadance.Back_DecaDance_PFI.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserImageUpdateRequestDTO(
    @NotBlank(message = "La URL de la imagen es obligatoria")
    String image
) {

}