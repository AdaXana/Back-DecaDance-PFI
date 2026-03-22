package com.decadance.Back_DecaDance_PFI.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.decadance.Back_DecaDance_PFI.dto.request.SongRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.SongResponseDTO;
import com.decadance.Back_DecaDance_PFI.entity.Song;
import com.decadance.Back_DecaDance_PFI.mapper.SongMapper;
import com.decadance.Back_DecaDance_PFI.repository.SongRepository;

import jakarta.transaction.Transactional;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final SongMapper songMapper;

    public SongServiceImpl(SongRepository songRepository, SongMapper songMapper){
        this.songRepository = songRepository;
        this.songMapper = songMapper;
    }

    @Override
    @Transactional
    public SongResponseDTO createSong(SongRequestDTO request) {
        if (songRepository.findByDeezerId(request.deezerId()).isPresent()) {
            throw new RuntimeException("Ya existe una canción registrada con el Deezer ID: " + request.deezerId());
        }
        Song song = songMapper.toEntity(request);
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
    public SongResponseDTO getSongById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException( "Canción no encontrada con ID: " + id));
        return songMapper.toResponse(song);
    }

    @Override
    @Transactional
    public SongResponseDTO updateTitle(Long id, String newTitle) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canción no encontrada con ID: " + id));
        song.setTitle(newTitle);
        return songMapper.toResponse(songRepository.save(song));
    }

    @Override
    @Transactional
    public SongResponseDTO updateArtist(Long id, String newArtist) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canción no encontrada con ID: " + id));
        song.setArtist(newArtist);
        return songMapper.toResponse(songRepository.save(song));
    }

    @Override
    @Transactional
    public SongResponseDTO updateYear(Long id, Integer newYear) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canción no encontrada con ID: " + id));
        song.setYear(newYear);
        return songMapper.toResponse(songRepository.save(song));
    }

    @Override
    @Transactional
    public SongResponseDTO updateCoverUrl(Long id, String newCoverUrl) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canción no encontrada con ID: " + id));
        song.setCoverUrl(newCoverUrl);
        return songMapper.toResponse(songRepository.save(song));
    }

    @Override
    @Transactional
    public SongResponseDTO updateStatus(Long id, Boolean isActive) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canción no encontrada con ID: " + id));
        song.setIsActive(isActive);
        return songMapper.toResponse(songRepository.save(song));
    }

    @Override
    @Transactional
    public void deleteSong(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canción no encontrada con ID: " + id));
        songRepository.delete(song);
    }

}