package com.decadance.Back_DecaDance_PFI.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decadance.Back_DecaDance_PFI.service.GamePlayerService;

@RestController
@RequestMapping("api/v1/gameplayers")

public class GamePlayerController {

    private final GamePlayerService gamePlayerService;

    public GamePlayerController(GamePlayerService gamePlayerService){
        this.gamePlayerService = gamePlayerService;
    }

    // @PostMapping
    // @GetMapping
    // @PatchMapping
    // @DeleteMapping

}