package com.decadance.Back_DecaDance_PFI.dto.request;

import jakarta.validation.constraints.NotNull;

public record SongYearUpdateRequestDTO(
    
    @NotNull(message = "El año es obligatorio")
    Integer year
) {

}