package com.decadance.Back_DecaDance_PFI.dto.request;

import jakarta.validation.constraints.NotNull;

public record SongGenreUpdateRequestDTO(

    @NotNull(message = "El ID del género es obligatorio") 
    Long idGenre

) {

}
