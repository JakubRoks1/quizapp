package com.example.quizapp.mappers;

import com.example.quizapp.entity.QuizEntity;
import com.example.quizapp.json.QuizJson;
import com.example.quizapp.model.Quiz;
import org.mapstruct.*;

@Mapper(uses = AnswerMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface QuizMapper {
    QuizEntity mapToQuizEntity(Quiz quiz);

    Quiz mapToQuiz(QuizEntity quizEntity);

    QuizJson mapToQuizJson(Quiz quiz);

    @Mapping(target = "id", ignore = true)
    Quiz mapToQuiz(QuizJson quizJson);

    QuizJson mapToQuizJson(QuizEntity quizEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateQuizFromDto(Quiz source, @MappingTarget QuizEntity target);

}
