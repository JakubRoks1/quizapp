package com.example.quizapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "questions")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String questionText;
}
