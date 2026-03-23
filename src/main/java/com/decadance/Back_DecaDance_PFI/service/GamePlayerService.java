package com.decadance.Back_DecaDance_PFI.service;

import java.util.UUID;

import com.decadance.Back_DecaDance_PFI.entity.Game;
import com.decadance.Back_DecaDance_PFI.entity.User;

public interface GamePlayerService {

    void joinAsHost(Game game, User user);
    void joinGame(Game game, User user, String nickname);
    boolean isUserHostOfGame(UUID idGame, Long userId);
    void updatePlayerScore(Long idGamePlayer, Boolean isSuccess);

}