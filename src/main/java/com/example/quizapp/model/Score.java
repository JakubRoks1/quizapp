package com.example.quizapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Score {

    private Long id;
    private int score;
    private User user;
}
