package com.decadance.Back_DecaDance_PFI.service;

import java.util.List;

import com.decadance.Back_DecaDance_PFI.dto.response.GenreResponseDTO;
import com.decadance.Back_DecaDance_PFI.entity.Genre;

public interface GenreService {
    
    List<GenreResponseDTO> getAllGenres();
    Genre getOrCreateGenre(String genreName);

}