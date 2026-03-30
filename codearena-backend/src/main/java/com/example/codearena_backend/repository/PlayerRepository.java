package com.example.codearena_backend.repository;

import com.example.codearena_backend.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}