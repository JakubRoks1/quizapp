package com.example.quizapp.json;

import java.time.LocalDateTime;

public record ErrorJson(int code,
                        String message,
                        LocalDateTime timestamp) {
}
