package com.decadance.Back_DecaDance_PFI.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decadance.Back_DecaDance_PFI.service.GameService;

@RestController
@RequestMapping("api/v1/games")

public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    // @PostMapping
    // @GetMapping
    // @PatchMapping
    // @DeleteMapping

}