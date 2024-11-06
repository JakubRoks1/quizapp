package com.example.quizapp.repository;

import com.example.quizapp.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<QuizEntity, Long> {

    @Query("SELECT q FROM QuizEntity q LEFT JOIN FETCH q.questions WHERE q.id = :id")
    Optional<QuizEntity> findByIdWithQuestions(@Param("id") Long id);
}
