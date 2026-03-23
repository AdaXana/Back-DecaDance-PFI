package com.decadance.Back_DecaDance_PFI.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.decadance.Back_DecaDance_PFI.dto.request.GameCreateRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.GameEndRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.GameJoinRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.GameScoreRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.GameResponseDTO;
import com.decadance.Back_DecaDance_PFI.service.GameService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/games")

public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<GameResponseDTO> createGame(@Valid @RequestBody GameCreateRequestDTO dto) {
        GameResponseDTO response = gameService.createGame(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/join")
    public ResponseEntity<Map<String, String>> joinGame(@Valid @RequestBody GameJoinRequestDTO dto) {
        gameService.joinGame(dto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Te has unido a la partida con éxito");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/start/{idGame}")
    public ResponseEntity<GameResponseDTO> startGame(@PathVariable UUID idGame,@Valid @RequestBody GameCreateRequestDTO dto) {
        GameResponseDTO response = gameService.startGame(idGame, dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{idGame}/score")
    public ResponseEntity<Map<String, String>> registerScore(@PathVariable UUID idGame, @Valid @RequestBody GameScoreRequestDTO request) {
        gameService.registerScore(idGame, request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Puntuación actualizada correctamente.");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{idGame}/end")
    public ResponseEntity<GameResponseDTO> endGame(@PathVariable UUID idGame, @RequestBody(required = false) GameEndRequestDTO request) {
        GameResponseDTO response = gameService.endGame(idGame, request);
        return ResponseEntity.ok(response);
    }

}