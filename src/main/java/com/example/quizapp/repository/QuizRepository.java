package com.example.quizapp.repository;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<QuizEntity, Long> {
}
