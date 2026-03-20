package com.decadance.Back_DecaDance_PFI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "game_players")
@Data
@NoArgsConstructor
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGamePlayer;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Boolean isHost = false;

    @Column(nullable = false)
    private Integer cardsWon = 0;

    @Column(nullable = false)
    private Integer picksRemaining = 3;

    @Column(nullable = false)
    private Integer matchSuccesses = 0;

    @Column(nullable = false)
    private Integer matchFails = 0;

}
