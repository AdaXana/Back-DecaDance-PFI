package com.decadance.Back_DecaDance_PFI.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SongArtistUpdateRequestDTO(
    
    @NotBlank(message = "El artista es obligatorio")
    String artist
) {

}