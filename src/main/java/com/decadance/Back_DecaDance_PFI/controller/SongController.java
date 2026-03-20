package com.decadance.Back_DecaDance_PFI.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decadance.Back_DecaDance_PFI.service.SongService;

@RestController
@RequestMapping("api/v1/songs")

public class SongController {

    private final SongService songService;

    public SongController(SongService songService){
        this.songService = songService;
    }

    // @PostMapping
    // @GetMapping
    // @PatchMapping
    // @DeleteMapping

}