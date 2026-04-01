package com.decadance.Back_DecaDance_PFI.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.decadance.Back_DecaDance_PFI.dto.response.GameResponseDTO;
import com.decadance.Back_DecaDance_PFI.entity.Game;

@Mapper(componentModel = "spring")
public interface GameMapper {

    @Mapping(target = "mode", expression = "java(game.getMode().name())")
    @Mapping(target = "status", expression = "java(game.getStatus().name())")
    @Mapping(target = "idGenre", source = "genre.idGenre")
    @Mapping(target = "genreName", source = "genre.genreName")
    @Mapping(target = "winnerId", source = "winner.idUser")
    
    GameResponseDTO toResponse(Game game);

}