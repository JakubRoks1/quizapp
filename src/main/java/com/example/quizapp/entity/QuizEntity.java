package com.example.quizapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class QuizEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String quizCategory;
    private String description;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private UserEntity user;
//
//    @OneToMany(mappedBy = "quiz")
//    private Set<QuestionEntity> questions;

}
