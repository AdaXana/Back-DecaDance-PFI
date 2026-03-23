package com.decadance.Back_DecaDance_PFI.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SongTitleUpdateRequestDTO(
    
    @NotBlank(message = "El título es obligatorio")
    String title
) {

}