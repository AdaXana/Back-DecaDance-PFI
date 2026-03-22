package com.decadance.Back_DecaDance_PFI.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record SongRequestDTO(

    @NotNull(message = "El ID de Deezer es obligatorio")
    Long deezerId,

    @NotBlank(message = "El título es obligatorio")
    String title,

    @NotBlank(message = "El artista es obligatorio")
    String artist,

    @NotNull(message = "El año es obligatorio")
    Integer year,

    @NotBlank(message = "El género es obligatorio")
    String genre,

    @NotBlank(message = "La URL de la portada es obligatoria")
    @URL(message = "Debe ser una URL válida")
    String coverUrl,

    @NotBlank(message = "La URL del preview es obligatoria")
    @URL(message = "Debe ser una URL válida")
    String previewUrl
) {

}