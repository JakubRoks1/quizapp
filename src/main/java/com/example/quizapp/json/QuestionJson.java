package com.example.quizapp.json;

import lombok.Data;

@Data
public class QuestionJson {
    private Long id;
    private String questionText;
    private String answer;
}

