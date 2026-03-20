package com.decadance.Back_DecaDance_PFI.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.decadance.Back_DecaDance_PFI.entity.GamePlayer;

public interface GamePlayerRepository extends JpaRepository<GamePlayer, Long> {

    List<GamePlayer> findByGame_IdGame(UUID idGame);
}