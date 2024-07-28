package com.example.quizapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private RoleEntity role;
//
//    @OneToMany(mappedBy = "user")
//    private Set<ScoreEntity> scores;
//
//    @OneToMany(mappedBy = "user")
//    private Set<QuizEntity> quizzes;
//
//    @OneToMany(mappedBy = "user")
//    private Set<QuestionEntity> questions;
}
