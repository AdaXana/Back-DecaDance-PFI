package com.decadance.Back_DecaDance_PFI.dto.response;

public record SongResponseDTO(
    Long idSong,
    Long deezerId,
    String title,
    String artist,
    Integer year,
    String genre,
    String coverUrl,
    String previewUrl,
    Boolean isActive
) {
    
}