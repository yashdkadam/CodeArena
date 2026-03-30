package com.example.codearena_backend.dto;

import lombok.Data;

@Data
public class SubmitRequest {
    private Long sessionId;
    private Long playerId;
    private String output;
}
