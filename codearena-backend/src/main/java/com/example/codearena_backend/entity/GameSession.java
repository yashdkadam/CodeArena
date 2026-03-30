package com.example.codearena_backend.entity;

import com.example.codearena_backend.enums.GameStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Player player1;

    @ManyToOne
    private Player player2;

    @ManyToOne
    private Problem problem;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    private Long winnerId;
}
