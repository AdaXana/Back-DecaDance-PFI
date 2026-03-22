package com.decadance.Back_DecaDance_PFI.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.decadance.Back_DecaDance_PFI.dto.request.SongArtistUpdateRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.SongCoverUrlUpdateRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.SongRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.SongStatusUpdateRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.SongTitleUpdateRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.SongYearUpdateRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.SongResponseDTO;
import com.decadance.Back_DecaDance_PFI.service.SongService;

@RestController
@RequestMapping("api/v1/songs")

public class SongController {

    private final SongService songService;

    public SongController(SongService songService){
        this.songService = songService;
    }

    @PostMapping
    public ResponseEntity<SongResponseDTO> createSong(@Valid @RequestBody SongRequestDTO request) {
        SongResponseDTO response = songService.createSong(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SongResponseDTO>> getAllSongs() {
        List<SongResponseDTO> response = songService.getAllSongs();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponseDTO> getSongById(@PathVariable Long id) {
        SongResponseDTO response = songService.getSongById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/title/{id}")
    public ResponseEntity<SongResponseDTO> updateTitle(@PathVariable Long id, @Valid @RequestBody SongTitleUpdateRequestDTO payload) {
        SongResponseDTO response = songService.updateTitle(id, payload.title());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/artist/{id}")
    public ResponseEntity<SongResponseDTO> updateArtist(@PathVariable Long id, @Valid @RequestBody SongArtistUpdateRequestDTO payload) {
        SongResponseDTO response = songService.updateArtist(id, payload.artist());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/year/{id}")
    public ResponseEntity<SongResponseDTO> updateYear(@PathVariable Long id, @Valid @RequestBody SongYearUpdateRequestDTO payload) {
        SongResponseDTO response = songService.updateYear(id, payload.year());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/cover/{id}")
    public ResponseEntity<SongResponseDTO> updateCoverUrl(@PathVariable Long id, @Valid @RequestBody SongCoverUrlUpdateRequestDTO payload) {
        SongResponseDTO response = songService.updateCoverUrl(id, payload.coverUrl());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<SongResponseDTO> updateStatus(
            @PathVariable Long id, 
            @Valid @RequestBody SongStatusUpdateRequestDTO payload) {
        SongResponseDTO response = songService.updateStatus(id, payload.isActive());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}