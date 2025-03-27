package com.example.quizapp.json;

import java.time.LocalDateTime;

public record ErrorJson(int code,
                        String errorCode,
                        String message,
                        LocalDateTime timestamp) {
}
