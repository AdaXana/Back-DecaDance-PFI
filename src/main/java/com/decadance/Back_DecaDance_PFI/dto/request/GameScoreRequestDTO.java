package com.decadance.Back_DecaDance_PFI.dto.request;

import jakarta.validation.constraints.NotNull;

public record GameScoreRequestDTO(

    @NotNull(message = "El ID del jugador en la partida es obligatorio")
    Long idGamePlayer,

    @NotNull(message = "Indica si ha sido un acierto (true) o fallo (false)")
    Boolean isSuccess
) {

}