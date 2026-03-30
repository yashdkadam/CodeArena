package com.example.codearena_backend.repository;

import com.example.codearena_backend.entity.GameSession;
import com.example.codearena_backend.enums.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameSessionRepository extends JpaRepository<GameSession, Long> {

    Optional<GameSession> findFirstByStatus(GameStatus status);

    Optional<GameSession> findFirstByStatusOrderByIdAsc(GameStatus gameStatus);
}