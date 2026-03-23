package com.decadance.Back_DecaDance_PFI.dto.request;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;

public record GameJoinRequestDTO(

    @NotNull(message = "El ID de la partida es obligatorio")
    UUID idGame,

    Long userId,

    String nickname
) {

}