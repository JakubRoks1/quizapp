package com.example.quizapp.model;

import lombok.Data;

@Data
public class Quiz {
    private Long id;

    private String quizCategory;
    private String description;
}
