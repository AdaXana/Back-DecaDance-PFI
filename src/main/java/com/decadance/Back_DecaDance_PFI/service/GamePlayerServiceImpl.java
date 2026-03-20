package com.decadance.Back_DecaDance_PFI.service;

import org.springframework.stereotype.Service;

import com.decadance.Back_DecaDance_PFI.repository.GamePlayerRepository;

@Service
public class GamePlayerServiceImpl implements GamePlayerService {

    private final GamePlayerRepository gamePlayerRepository;

    public GamePlayerServiceImpl(GamePlayerRepository gamePlayerRepository){
        this.gamePlayerRepository = gamePlayerRepository;
    }

    // @Override

    
}
