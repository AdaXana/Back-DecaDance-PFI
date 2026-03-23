package com.decadance.Back_DecaDance_PFI.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameResponseDTO(

    UUID idGame,
    String status,
    LocalDateTime createdAt

) {

}