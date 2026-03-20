package com.decadance.Back_DecaDance_PFI.service;

import org.springframework.stereotype.Service;

import com.decadance.Back_DecaDance_PFI.repository.GameRepository;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    // @Override
    

}
