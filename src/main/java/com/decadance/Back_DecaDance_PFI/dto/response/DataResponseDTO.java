package com.decadance.Back_DecaDance_PFI.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record DataResponseDTO(
    Long id,
    String title,
    String preview,
    ArtistDTO artist,
    AlbumDTO album
) {
    public record ArtistDTO(String name) {}

    public record AlbumDTO(
        Long id,
        @JsonProperty("cover_medium") String coverMedium,
        @JsonProperty("release_date") String releaseDate,
        GenresDTO genres 
    ) {}

    public record GenresDTO(List<GenreDataDTO> data) {}
    public record GenreDataDTO(String name) {}
}