package com.decadance.Back_DecaDance_PFI.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.decadance.Back_DecaDance_PFI.entity.enums.GameMode;
import com.decadance.Back_DecaDance_PFI.entity.enums.GameStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idGame;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatus status = GameStatus.WAITING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameMode mode;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = true)
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "winner_id", nullable = true)
    private User winner;
}