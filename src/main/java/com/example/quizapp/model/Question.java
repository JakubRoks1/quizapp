package com.example.quizapp.model;

import lombok.Data;

@Data
public class Question {
    private Long id;

    private String question;

    private String answer1;

    private String answer2;

    private String answer3;

    private String answer4;

    private String correctAnswer;
}
