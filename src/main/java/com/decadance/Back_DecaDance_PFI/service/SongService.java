package com.decadance.Back_DecaDance_PFI.service;

import java.util.List;

import com.decadance.Back_DecaDance_PFI.dto.request.SongRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.SongResponseDTO;

public interface SongService {
    SongResponseDTO createSong(SongRequestDTO request);
    List<SongResponseDTO> getAllSongs();
    SongResponseDTO getSongById(Long id);
    SongResponseDTO updateTitle(Long id, String newTitle);
    SongResponseDTO updateArtist(Long id, String newArtist);
    SongResponseDTO updateYear(Long id, Integer newYear);
    SongResponseDTO updateCoverUrl(Long id, String newCoverUrl);
    SongResponseDTO updateStatus(Long id, Boolean isActive);
    void deleteSong(Long id);

}
