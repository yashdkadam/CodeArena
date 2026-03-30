package com.example.codearena_backend.service;

import com.example.codearena_backend.dto.*;
import com.example.codearena_backend.entity.*;
import com.example.codearena_backend.enums.GameStatus;
import com.example.codearena_backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GameService {

    private final PlayerRepository playerRepo;
    private final GameSessionRepository sessionRepo;
    private final ProblemRepository problemRepo;
    private final ExecutionService executionService;
    private final WebSocketService ws;

    public GameService(PlayerRepository playerRepo,
                       GameSessionRepository sessionRepo,
                       ProblemRepository problemRepo,
                       ExecutionService executionService,
                       WebSocketService ws) {
        this.playerRepo = playerRepo;
        this.sessionRepo = sessionRepo;
        this.problemRepo = problemRepo;
        this.executionService = executionService;
        this.ws = ws;
    }

    public GameSession getSession(Long id) {

        System.out.println("id" + id);
        return sessionRepo.findById(id).orElseThrow();
    }

    public GameSession join(JoinRequest req) {

        Player player = playerRepo.save(new Player(null, req.getUsername()));

        // 🔥 Get oldest waiting session
        GameSession session = sessionRepo
                .findFirstByStatusOrderByIdAsc(GameStatus.WAITING)
                .orElse(null);

        if (session == null) {
            // 🧑 First player creates session
            session = new GameSession();
            session.setPlayer1(player);
            session.setStatus(GameStatus.WAITING);

        } else {
            // 👥 Second player joins
            session.setPlayer2(player);
            session.setStatus(GameStatus.STARTED);

            // 🎲 Assign random problem
            session.setProblem(problemRepo.findRandomProblem());

            // 🔥 Send event
            ws.send(session.getId(), Map.of(
                    "event", "GAME_STARTED",
                    "problem", session.getProblem()
            ));
        }

        return sessionRepo.save(session);
    }

    public void submit(SubmitRequest req) throws Exception {

        GameSession session = sessionRepo.findById(req.getSessionId()).orElseThrow();

        if (session.getStatus() == GameStatus.FINISHED) return;

        System.out.println(session.getProblem().getOutput());
        System.out.println(req.getOutput());

        if (session.getProblem().getOutput().equals(req.getOutput())) {
            session.setStatus(GameStatus.FINISHED);
            session.setWinnerId(req.getPlayerId());
            sessionRepo.save(session);

            ws.send(session.getId(), Map.of(
                    "event", "FINISHED",
                    "winner", req.getPlayerId()
            ));
        } else {
            ws.send(session.getId(), Map.of(
                    "event", "SUBMITTED",
                    "player", req.getPlayerId()
            ));
        }
    }
}
