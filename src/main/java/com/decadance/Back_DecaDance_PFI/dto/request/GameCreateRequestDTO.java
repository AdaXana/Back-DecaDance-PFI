package com.decadance.Back_DecaDance_PFI.dto.request;

import jakarta.validation.constraints.NotNull;

public record GameCreateRequestDTO(

    @NotNull(message = "El ID del usuario creador es obligatorio")
    Long userId
    
) {

}