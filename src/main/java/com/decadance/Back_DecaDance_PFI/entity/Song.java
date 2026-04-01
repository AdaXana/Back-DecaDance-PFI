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
@Table(name = "songs")
@Data
@NoArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSong;

    @Column(nullable = false, unique = true)
    private Long deezerId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false, length = 500)
    private String coverUrl;

    @Column(nullable = false, length = 500)
    private String previewUrl;

    @Column(nullable = false)
    private Boolean isActive = true;
    
    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;
}