package com.decadance.Back_DecaDance_PFI.service;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.decadance.Back_DecaDance_PFI.dto.request.GameCreateRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.GameEndRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.GameJoinRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.GameScoreRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.GameResponseDTO;
import com.decadance.Back_DecaDance_PFI.entity.Game;
import com.decadance.Back_DecaDance_PFI.entity.User;
import com.decadance.Back_DecaDance_PFI.entity.enums.Status;
import com.decadance.Back_DecaDance_PFI.repository.GameRepository;
import com.decadance.Back_DecaDance_PFI.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final GamePlayerService gamePlayerService;

    public GameServiceImpl(GameRepository gameRepository, UserRepository userRepository, GamePlayerService gamePlayerService) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.gamePlayerService = gamePlayerService;
    }

    @Override
    @Transactional
    public GameResponseDTO createGame(GameCreateRequestDTO dto) {

        User hostUser = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.userId()));

        Game game = new Game();
        game.setStatus(Status.WAITING);
        game.setCreatedAt(LocalDateTime.now());
        Game savedGame = gameRepository.save(game);

        gamePlayerService.joinAsHost(savedGame, hostUser);

        return new GameResponseDTO(
                savedGame.getIdGame(),
                savedGame.getStatus().name(),
                savedGame.getCreatedAt());
    }

    @Override
    @Transactional
    public void joinGame(GameJoinRequestDTO dto) {
        Game game = getGameAndVerifyStatus(
            dto.idGame(), Status.WAITING, "No puedes unirte: la partida ya ha empezado o ha finalizado.");
        User joiningUser = null;
        if (dto.userId() != null) {
            joiningUser = userRepository.findById(dto.userId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        }
        gamePlayerService.joinGame(game, joiningUser, dto.nickname());
    }

    @Override
    @Transactional
    public GameResponseDTO startGame(UUID idGame, GameCreateRequestDTO dto) {
        Game game = getGameAndVerifyStatus(
            idGame, Status.WAITING, "La partida ya ha empezado o finalizado.");
        boolean isAuthorizedHost = gamePlayerService.isUserHostOfGame(idGame, dto.userId());
        if (!isAuthorizedHost) {
            throw new RuntimeException("Acceso denegado: Solo el Host puede iniciar la partida.");
        }
        game.setStatus(Status.IN_PROGRESS);
        Game savedGame = gameRepository.save(game);
        return new GameResponseDTO(
                savedGame.getIdGame(),
                savedGame.getStatus().name(),
                savedGame.getCreatedAt()
        );
    }

    @Override
    @Transactional
    public void registerScore(UUID idGame, GameScoreRequestDTO dto) {
        getGameAndVerifyStatus(
            idGame, Status.IN_PROGRESS, "No se pueden registrar puntos: la partida no está en progreso.");
        gamePlayerService.updatePlayerScore(dto.idGamePlayer(), dto.isSuccess());
    }

    @Override
    @Transactional
    public GameResponseDTO endGame(UUID idGame, GameEndRequestDTO dto) {
        Game game = getGameAndVerifyStatus(
            idGame, Status.IN_PROGRESS, "No se puede finalizar una partida que no está en progreso.");
        game.setStatus(Status.FINISHED);
        if (dto != null && dto.winnerUserId() != null) {
            User winner = userRepository.findById(dto.winnerUserId())
                    .orElseThrow(() -> new RuntimeException("El usuario ganador no existe en la base de datos."));
            game.setWinner(winner);
        }
        Game savedGame = gameRepository.save(game);
        return new GameResponseDTO(
                savedGame.getIdGame(),
                savedGame.getStatus().name(),
                savedGame.getCreatedAt()
        );
    }


    private Game getGameAndVerifyStatus(UUID idGame, Status expectedStatus, String errorMessage) {
        Game game = gameRepository.findById(idGame)
                .orElseThrow(() -> new RuntimeException("La partida no existe."));
                
        if (!game.getStatus().equals(expectedStatus)) {
            throw new RuntimeException(errorMessage);
        }
        
        return game;}

}