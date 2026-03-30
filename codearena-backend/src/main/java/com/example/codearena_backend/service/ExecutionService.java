package com.example.codearena_backend.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

@Service
public class ExecutionService {

    public boolean execute(String code, String language, String input, String expectedOutput) throws Exception {

        String output = switch (language.toLowerCase()) {
            case "cpp" -> runCpp(code, input);
            case "java" -> runJava(code, input);
            case "python" -> runPython(code, input);
            case "javascript" -> runJs(code, input);
            default -> throw new RuntimeException("Unsupported language");
        };

        return output.trim().equals(expectedOutput.trim());
    }

    private String runCpp(String code, String input) throws Exception {
        Path dir = Files.createTempDirectory("codearena");
        Path file = dir.resolve("main.cpp");

        Files.writeString(file, code);

        Process compile = new ProcessBuilder("g++", file.toString(), "-o", "main")
                .directory(dir.toFile())
                .start();

        compile.waitFor();

        Process run = new ProcessBuilder("./main")
                .directory(dir.toFile())
                .start();

        return processIO(run, input);
    }

    private String runPython(String code, String input) throws Exception {
        Path dir = Files.createTempDirectory("codearena");
        Path file = dir.resolve("main.py");

        Files.writeString(file, code);

        Process run = new ProcessBuilder("python3", file.toString())
                .directory(dir.toFile())
                .start();

        return processIO(run, input);
    }

    private String runJava(String code, String input) throws Exception {
        Path dir = Files.createTempDirectory("codearena");
        Path file = dir.resolve("Main.java");

        Files.writeString(file, code);

        new ProcessBuilder("javac", file.toString())
                .directory(dir.toFile())
                .start().waitFor();

        Process run = new ProcessBuilder("java", "Main")
                .directory(dir.toFile())
                .start();

        return processIO(run, input);
    }

    private String runJs(String code, String input) throws Exception {
        Path dir = Files.createTempDirectory("codearena");
        Path file = dir.resolve("main.js");

        Files.writeString(file, code);

        Process run = new ProcessBuilder("node", file.toString())
                .directory(dir.toFile())
                .start();

        return processIO(run, input);
    }

    private String processIO(Process process, String input) throws Exception {

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(process.getOutputStream()))) {
            writer.write(input);
            writer.flush();
        }

        boolean finished = process.waitFor(2, TimeUnit.SECONDS);

        if (!finished) {
            process.destroy();
            return "TLE";
        }

        return new String(process.getInputStream().readAllBytes());
    }
}
