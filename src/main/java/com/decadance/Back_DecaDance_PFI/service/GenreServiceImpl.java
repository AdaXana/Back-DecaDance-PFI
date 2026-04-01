package com.decadance.Back_DecaDance_PFI.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.decadance.Back_DecaDance_PFI.dto.response.GenreResponseDTO;
import com.decadance.Back_DecaDance_PFI.entity.Genre;
import com.decadance.Back_DecaDance_PFI.mapper.GenreMapper;
import com.decadance.Back_DecaDance_PFI.repository.GenreRepository;

@Service
public class GenreServiceImpl implements GenreService{

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    @Override
    public List<GenreResponseDTO> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(genreMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Genre getOrCreateGenre(String genreName) {
        return genreRepository.findByGenreName(genreName)
                .orElseGet(() -> {
                    Genre newGenre = new Genre();
                    newGenre.setGenreName(genreName);
                    newGenre.setStageImageUrl("CoverDefault.png");
                    return genreRepository.save(newGenre);
                });
    }   

}
