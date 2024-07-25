package com.example.quizapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ScoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private UserEntity user;
}
