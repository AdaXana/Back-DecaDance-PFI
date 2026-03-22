package com.decadance.Back_DecaDance_PFI.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record SongCoverUrlUpdateRequestDTO(

    @NotBlank(message = "La URL de la portada es obligatoria")
    @URL(message = "Debe ser una URL válida")
    String coverUrl
) {
    
}