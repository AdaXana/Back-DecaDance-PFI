package com.decadance.Back_DecaDance_PFI.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.decadance.Back_DecaDance_PFI.dto.request.SongRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.SongResponseDTO;
import com.decadance.Back_DecaDance_PFI.entity.Genre;
import com.decadance.Back_DecaDance_PFI.entity.Song;
import com.decadance.Back_DecaDance_PFI.mapper.SongMapper;
import com.decadance.Back_DecaDance_PFI.repository.GenreRepository;
import com.decadance.Back_DecaDance_PFI.repository.SongRepository;
import jakarta.transaction.Transactional;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final GenreRepository genreRepository;
    private final SongMapper songMapper;

    public SongServiceImpl(SongRepository songRepository,    GenreRepository genreRepository, SongMapper songMapper){
        this.songRepository = songRepository;
        this.genreRepository = genreRepository;
        this.songMapper = songMapper;
    }

    @Override
    @Transactional
    public SongResponseDTO createSong(SongRequestDTO dto) {
        if (songRepository.findByDeezerId(dto.deezerId()).isPresent()) {
            throw new RuntimeException("Ya existe una canción registrada con el Deezer ID: " + dto.deezerId());
        }
        Genre genre = genreRepository.findById(dto.idGenre())
                .orElseThrow(() -> new RuntimeException("Género no encontrado con ID: " + dto.idGenre()));

        Song song = songMapper.toEntity(dto);
        song.setGenre(genre);
        Song savedSong = songRepository.save(song);
        return songMapper.toResponse(savedSong);
    }

    @Override
    public List<SongResponseDTO> getAllSongs() {
        return songRepository.findAll().stream()
                .map(songMapper::toResponse)
                .toList();
    }

    @Override
    public List<SongResponseDTO> getActiveSongs() {
        return songRepository.findByIsActiveTrue().stream()
                .map(songMapper::toResponse)
                .toList();
    }

    @Override
    public SongResponseDTO getSongById(Long id) {
        return songMapper.toResponse(getSongByIdOrThrow(id));
    }

    @Override
    @Transactional
    public SongResponseDTO updateTitle(Long id, String newTitle) {
        Song song = getSongByIdOrThrow(id);
        song.setTitle(newTitle);
        return songMapper.toResponse(songRepository.save(song));
    }

    @Override
    @Transactional
    public SongResponseDTO updateArtist(Long id, String newArtist) {
        Song song = getSongByIdOrThrow(id);
        song.setArtist(newArtist);
        return songMapper.toResponse(songRepository.save(song));
    }

    @Override
    @Transactional
    public SongResponseDTO updateYear(Long id, Integer newYear) {
        Song song = getSongByIdOrThrow(id);
        song.setYear(newYear);
        return songMapper.toResponse(songRepository.save(song));
    }

    @Override
    @Transactional
    public SongResponseDTO updateCoverUrl(Long id, String newCoverUrl) {
        Song song = getSongByIdOrThrow(id);
        song.setCoverUrl(newCoverUrl);
        return songMapper.toResponse(songRepository.save(song));
    }

    @Override
    @Transactional
    public SongResponseDTO updateStatus(Long id, Boolean isActive) {
        Song song = getSongByIdOrThrow(id);
        song.setIsActive(isActive);
        return songMapper.toResponse(songRepository.save(song));
    }

    @Override
    @Transactional
    public SongResponseDTO updateGenre(Long id, Long newIdGenre) {
        Song song = getSongByIdOrThrow(id);
        Genre newGenre = genreRepository.findById(newIdGenre)
                .orElseThrow(() -> new RuntimeException("Nuevo género no encontrado"));
        song.setGenre(newGenre);
        return songMapper.toResponse(songRepository.save(song));
    }

    @Override
    @Transactional
    public void deleteSong(Long id) {
        Song song = getSongByIdOrThrow(id);
        songRepository.delete(song);
    }


    private Song getSongByIdOrThrow(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canción no encontrada con ID: " + id));
    }

}