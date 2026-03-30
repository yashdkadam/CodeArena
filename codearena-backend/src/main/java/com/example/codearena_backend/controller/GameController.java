package com.example.codearena_backend.controller;

import com.example.codearena_backend.dto.JoinRequest;
import com.example.codearena_backend.entity.GameSession;
import com.example.codearena_backend.service.GameService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/join")
    public GameSession join(@RequestBody JoinRequest req) {
        return gameService.join(req);
    }

    @GetMapping("/{id}")
    public GameSession getSession(@PathVariable Long id) {
        return gameService.getSession(id);
    }
}
