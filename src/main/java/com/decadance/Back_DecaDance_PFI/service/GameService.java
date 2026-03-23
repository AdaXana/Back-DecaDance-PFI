package com.decadance.Back_DecaDance_PFI.service;

import java.util.UUID;

import com.decadance.Back_DecaDance_PFI.dto.request.GameCreateRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.GameEndRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.GameJoinRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.GameScoreRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.GameResponseDTO;

public interface GameService {

    GameResponseDTO createGame(GameCreateRequestDTO dto);
    void joinGame(GameJoinRequestDTO dto);
    GameResponseDTO startGame(UUID idGame, GameCreateRequestDTO dto);
    void registerScore(UUID idGame, GameScoreRequestDTO dto);
    GameResponseDTO endGame(UUID idGame, GameEndRequestDTO dto);

}