package com.example.quizapp.model;

import lombok.Data;

import java.util.Set;

@Data
public class Quiz {
    private Long id;

    private String quizCategory;
    private String description;

    private Set<Question> questions;
}
