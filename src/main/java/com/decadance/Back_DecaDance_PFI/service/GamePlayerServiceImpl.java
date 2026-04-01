package com.decadance.Back_DecaDance_PFI.service;

import java.util.UUID;
import org.springframework.stereotype.Service;
import com.decadance.Back_DecaDance_PFI.entity.Game;
import com.decadance.Back_DecaDance_PFI.entity.GamePlayer;
import com.decadance.Back_DecaDance_PFI.entity.User;
import com.decadance.Back_DecaDance_PFI.repository.GamePlayerRepository;
import jakarta.transaction.Transactional;

@Service
public class GamePlayerServiceImpl implements GamePlayerService {

    private final GamePlayerRepository gamePlayerRepository;

    public GamePlayerServiceImpl(GamePlayerRepository gamePlayerRepository) {
        this.gamePlayerRepository = gamePlayerRepository;
    }

    @Override
    @Transactional
    public void joinAsHost(Game game, User user) {
        GamePlayer hostPlayer = new GamePlayer();
        hostPlayer.setGame(game);
        hostPlayer.setUser(user);
        hostPlayer.setNickname(user.getUsername());
        hostPlayer.setIsHost(true);
        hostPlayer.setCardsWon(0);
        hostPlayer.setMatchSuccesses(0);
        hostPlayer.setMatchFails(0);
        hostPlayer.setPicksRemaining(3);

        gamePlayerRepository.save(hostPlayer);
    }

    @Override
    @Transactional
    public void joinGame(Game game, User user, String nickname) {
        GamePlayer player = new GamePlayer();
        player.setGame(game);
        player.setUser(user);

        if (user != null) {
            player.setNickname(user.getUsername());
        } else {
            player.setNickname(nickname != null && !nickname.trim().isEmpty() ? nickname : "Invitado");
        }
        player.setIsHost(false);
        player.setCardsWon(0);
        player.setMatchSuccesses(0);
        player.setMatchFails(0);
        player.setPicksRemaining(3);
        gamePlayerRepository.save(player);
    }

    @Override
    public boolean isUserHostOfGame(UUID idGame, Long userId) {
        return gamePlayerRepository.existsByGame_IdGameAndUser_IdUserAndIsHostTrue(idGame, userId);
    }

    @Override
    @Transactional
    public void updatePlayerScore(Long idGamePlayer, Boolean isSuccess) {
        GamePlayer player = gamePlayerRepository.findById(idGamePlayer)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado en esta partida."));
        if (!isSuccess && player.getPicksRemaining() <= 0) {
            throw new RuntimeException("No te quedan púas. No puedes desafiar a tu oponente.");
        }
        if (isSuccess) {
            player.setCardsWon(player.getCardsWon() + 1);
            player.setMatchSuccesses(player.getMatchSuccesses() + 1);
        } else {
            player.setMatchFails(player.getMatchFails() + 1);
            player.setPicksRemaining(player.getPicksRemaining() - 1);
        }
        gamePlayerRepository.save(player);
    }

}