package com.example.codearena_backend.controller;

import com.example.codearena_backend.dto.SubmitRequest;
import com.example.codearena_backend.service.GameService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/submit")
public class SubmissionController {

    private final GameService gameService;

    public SubmissionController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public void submit(@RequestBody SubmitRequest req) throws Exception {
        gameService.submit(req);
    }
}
