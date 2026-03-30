package com.example.codearena_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameResponse {
    private Long sessionId;
    private String status;
    private String problem;
    private String opponent;
    private Long winnerId;
}
