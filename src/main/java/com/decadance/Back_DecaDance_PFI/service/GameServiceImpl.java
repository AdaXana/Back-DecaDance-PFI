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
import com.decadance.Back_DecaDance_PFI.entity.Genre;
import com.decadance.Back_DecaDance_PFI.entity.User;
import com.decadance.Back_DecaDance_PFI.entity.enums.GameMode;
import com.decadance.Back_DecaDance_PFI.entity.enums.GameStatus;
import com.decadance.Back_DecaDance_PFI.mapper.GameMapper;
import com.decadance.Back_DecaDance_PFI.repository.GameRepository;
import com.decadance.Back_DecaDance_PFI.repository.GenreRepository;
import com.decadance.Back_DecaDance_PFI.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final GamePlayerService gamePlayerService;
    private final GameMapper gameMapper;    

    public GameServiceImpl(GameRepository gameRepository, UserRepository userRepository,
            GenreRepository genreRepository, GamePlayerService gamePlayerService, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.gamePlayerService = gamePlayerService;
        this.gameMapper = gameMapper;
    }

    @Override
    @Transactional
    public GameResponseDTO createGame(GameCreateRequestDTO dto) {

        User hostUser = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.userId()));

        Game game = new Game();
        game.setStatus(GameStatus.WAITING);
        game.setCreatedAt(LocalDateTime.now());
        game.setMode(GameMode.valueOf(dto.mode()));
        if (game.getMode() == GameMode.GENRE && dto.idGenre() != null) {
            Genre genre = genreRepository.findById(dto.idGenre())
                    .orElseThrow(() -> new RuntimeException("Género no encontrado"));
            game.setGenre(genre);
        }
        Game savedGame = gameRepository.save(game);
        gamePlayerService.joinAsHost(savedGame, hostUser);

        return gameMapper.toResponse(savedGame);
    }

    @Override
    @Transactional
    public void joinGame(GameJoinRequestDTO dto) {
        Game game = getGameAndVerifyStatus(
                dto.idGame(), GameStatus.WAITING, "No puedes unirte: la partida ya ha empezado o ha finalizado.");
        User joiningUser = (dto.userId() != null) ? userRepository.findById(dto.userId()).orElse(null) : null;
        gamePlayerService.joinGame(game, joiningUser, dto.nickname());
    }

    @Override
    @Transactional
    public GameResponseDTO startGame(UUID idGame, GameCreateRequestDTO dto) {
        Game game = getGameAndVerifyStatus(
                idGame, GameStatus.WAITING, "La partida ya ha empezado o finalizado.");
        if (!gamePlayerService.isUserHostOfGame(idGame, dto.userId())) {
            throw new RuntimeException("Acceso denegado: Solo el Host puede iniciar la partida.");
        }
        game.setStatus(GameStatus.IN_PROGRESS);
        return gameMapper.toResponse(gameRepository.save(game));
    }

    @Override
    @Transactional
    public void registerScore(UUID idGame, GameScoreRequestDTO dto) {
        getGameAndVerifyStatus(
                idGame, GameStatus.IN_PROGRESS, "No se pueden registrar puntos: la partida no está en progreso.");
        gamePlayerService.updatePlayerScore(dto.idGamePlayer(), dto.isSuccess());
    }

    @Override
    @Transactional
    public GameResponseDTO endGame(UUID idGame, GameEndRequestDTO dto) {
        Game game = getGameAndVerifyStatus(
                idGame, GameStatus.IN_PROGRESS, "No se puede finalizar una partida que no está en progreso.");
        game.setStatus(GameStatus.FINISHED);
        if (dto != null && dto.winnerUserId() != null) {
            User winner = userRepository.findById(dto.winnerUserId())
                    .orElseThrow(() -> new RuntimeException("El usuario ganador no existe en la base de datos."));
            game.setWinner(winner);
        }
        return gameMapper.toResponse(gameRepository.save(game));
    }
        

    private Game getGameAndVerifyStatus(UUID idGame, GameStatus expectedStatus, String errorMessage) {
        Game game = gameRepository.findById(idGame)
                .orElseThrow(() -> new RuntimeException("La partida no existe."));
        if (!game.getStatus().equals(expectedStatus)) {
            throw new RuntimeException(errorMessage);
        }
        return game;
    }

}