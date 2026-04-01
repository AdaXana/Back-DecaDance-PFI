package com.decadance.Back_DecaDance_PFI.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.decadance.Back_DecaDance_PFI.entity.Song;

public interface SongRepository extends JpaRepository<Song, Long> {

    Optional<Song> findByDeezerId(Long deezerId);
    List<Song> findByIsActiveTrue();
    List<Song> findByGenre_IdGenreAndIsActiveTrue(Long idGenre);
}