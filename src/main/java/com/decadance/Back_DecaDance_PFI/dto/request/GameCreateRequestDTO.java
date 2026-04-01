package com.decadance.Back_DecaDance_PFI.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GameCreateRequestDTO(

    @NotNull(message = "El ID del usuario creador es obligatorio")
    Long userId,

    @NotBlank(message = "El modo de juego es obligatorio (RANDOM, GENRE o CUSTOM)")
    String mode,

    Long idGenre
    
) {

}