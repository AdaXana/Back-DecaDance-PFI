package com.decadance.Back_DecaDance_PFI.dto.request;

import jakarta.validation.constraints.NotNull;

public record SongStatusUpdateRequestDTO(

    @NotNull(message = "El estado (isActive) es obligatorio")
    Boolean isActive
) {
    
}