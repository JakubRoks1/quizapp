package com.example.quizapp.json;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorJson {
    private int code;
    private String message;
    private LocalDateTime timestamp;
}
