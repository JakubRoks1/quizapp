package com.example.quizapp.json;

import lombok.Data;

import java.util.List;

@Data
public class QuizWithQuestionsJson {
    private Long id;
    private String quizCategory;
    private String description;
    private List<QuestionJson> questions;
}