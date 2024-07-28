package com.example.quizapp.repository;

import com.example.quizapp.entity.AnswerEntity;
import com.example.quizapp.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
}
