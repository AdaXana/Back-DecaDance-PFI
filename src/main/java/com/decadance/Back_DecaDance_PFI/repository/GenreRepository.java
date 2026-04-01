package com.decadance.Back_DecaDance_PFI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.decadance.Back_DecaDance_PFI.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByGenreName(String genreName);
    boolean existsByGenreName(String genreName);
}