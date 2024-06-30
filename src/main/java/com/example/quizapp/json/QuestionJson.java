package com.example.quizapp.json;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class QuestionJson {
        private Long id;
        private String questionText;
        private String answer;
    }

