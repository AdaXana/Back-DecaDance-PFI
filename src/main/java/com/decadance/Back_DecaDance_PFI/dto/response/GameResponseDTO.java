package com.decadance.Back_DecaDance_PFI.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameResponseDTO(

    UUID idGame,
    String mode,
    String status,
    LocalDateTime createdAt,
    Long idGenre,
    String genreName,
    Long winnerId

) {

}