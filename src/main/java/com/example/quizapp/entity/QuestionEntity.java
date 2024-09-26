package com.example.quizapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private QuizEntity quiz;

   @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private Set<AnswerEntity> answers;

//    @ManyToOne
//    @JoinColumn(name = "quiz_id")
//    private QuizEntity quiz;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private UserEntity user;
//
//    @OneToMany(mappedBy = "question")
//    private Set<AnswerEntity> answers;

}
