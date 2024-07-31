package com.example.quizapp.mappers;

import com.example.quizapp.entity.QuizEntity;
import com.example.quizapp.json.QuizJson;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.service.QuizService;
import org.mapstruct.Mapper;

@Mapper
public interface QuizMapper {
    QuizEntity mapToQuizEntity(QuizService quiz);

    Quiz mapToQuiz(QuizEntity quizEntity);

    QuizJson mapToQuizJson(Quiz quiz);

    Quiz mapToQuiz(QuizJson quizJson);
}
