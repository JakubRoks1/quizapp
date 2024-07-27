package com.example.quizapp.json;

import lombok.Data;

@Data
public class AnswerJson {
    private Long id;
    private String answerText;
    private boolean isCorrect;
    private Long questionId;
}
