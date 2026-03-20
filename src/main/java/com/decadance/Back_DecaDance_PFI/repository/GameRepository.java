package com.decadance.Back_DecaDance_PFI.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.decadance.Back_DecaDance_PFI.entity.Game;

public interface GameRepository extends JpaRepository <Game, UUID> {
    
}