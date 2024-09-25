package com.example.quizapp.mappers;

import com.example.quizapp.entity.QuizEntity;
import com.example.quizapp.json.QuizJson;
import com.example.quizapp.model.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface QuizMapper {
    QuizEntity mapToQuizEntity(Quiz quiz);

    Quiz mapToQuiz(QuizEntity quizEntity);

    QuizJson mapToQuizJson(Quiz quiz);

    @Mapping(target = "id", ignore = true)
    Quiz mapToQuiz(QuizJson quizJson);

    QuizJson mapToQuizJson(QuizEntity quizEntity);
}
