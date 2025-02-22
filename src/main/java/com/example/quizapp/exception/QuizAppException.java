package com.example.quizapp.exception;

import java.time.LocalDateTime;

public class QuizAppException extends RuntimeException {
    private final int code;
    private final LocalDateTime timestamp;

    public QuizAppException(int code, String message) {
        super(message);
        this.code = code;
        this.timestamp = LocalDateTime.now();
    }

    public int getCode() {
        return code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
