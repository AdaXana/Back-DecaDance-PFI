package com.decadance.Back_DecaDance_PFI.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.decadance.Back_DecaDance_PFI.dto.response.GenreResponseDTO;
import com.decadance.Back_DecaDance_PFI.entity.Genre;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    
    GenreResponseDTO toResponse(Genre entity);

}
