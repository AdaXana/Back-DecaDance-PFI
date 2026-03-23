package com.decadance.Back_DecaDance_PFI.entity;

import com.decadance.Back_DecaDance_PFI.entity.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = true, unique = true)
    private String email;

    @Column(nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = true)
    private String image;

    // metricas que por ahora no voy a segmentar en otra tabla.
    @Column(nullable = false)
    private Integer played = 0;

    @Column(nullable = false)
    private Integer victories = 0;

    @Column(nullable = false)
    private Integer successes = 0;

    @Column(nullable = false)
    private Integer fails = 0;

}
