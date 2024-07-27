package com.example.quizapp.model;

import lombok.Data;

@Data
public class Answer {
    private Long id;
    private String answerText;
    private boolean isCorrect;
    private Long questionId;
}