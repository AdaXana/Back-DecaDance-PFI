package com.decadance.Back_DecaDance_PFI.mapper;

import com.decadance.Back_DecaDance_PFI.dto.request.SongRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.DataResponseDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.SongResponseDTO;
import com.decadance.Back_DecaDance_PFI.entity.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SongMapper {

    @Mapping(target = "idSong", ignore = true)
    @Mapping(target = "deezerId", source = "id")
    @Mapping(target = "artist", source = "artist.name")
    @Mapping(target = "coverUrl", source = "album.coverMedium")
    @Mapping(target = "previewUrl", source = "preview")
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "year", ignore = true)
    @Mapping(target = "genre", ignore = true)

    Song toEntity (DataResponseDTO dto); 


    @Mapping(target = "idSong", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "genre", ignore = true)

    Song toEntity(SongRequestDTO dto);


    @Mapping(target = "idGenre", source = "genre.idGenre")
    @Mapping(target = "genreName", source = "genre.genreName")
    
    SongResponseDTO toResponse(Song entity);
}