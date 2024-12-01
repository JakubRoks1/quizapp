package com.example.quizapp.mappers;

import com.example.quizapp.entity.QuizEntity;
import com.example.quizapp.json.QuizFilteredJson;
import com.example.quizapp.json.QuizJson;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import org.mapstruct.*;

import java.util.Set;

@Mapper(uses = AnswerMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface QuizMapper {
    QuizEntity mapToQuizEntity(Quiz quiz);

    Quiz mapToQuiz(QuizEntity quizEntity);

    QuizFilteredJson mapToQuizFilteredJson(Quiz quiz);

//    @Mapping(target = "questionsCount", expression = "java(quiz.getQuestions().size())")
    @Mapping(target = "questionsCount", source = "questions", qualifiedByName = "getQuestionsCount")
    QuizJson mapToQuizJson(Quiz quiz);

    @Mapping(target = "questionsCount", source = "questions", qualifiedByName = "getQuestionsCount")
    @Mapping(target = "questions", ignore = true)
    QuizJson mapToQuizJsonWithCount(Quiz quiz);

//    default QuizJson mapToQuizJson(Quiz quiz, boolean isCount) {
//        return isCount ? mapToQuizJsonWithCount(quiz) : mapToQuizJsonWithQuestions(quiz);
//    }

    @Mapping(target = "id", ignore = true)
    Quiz mapToQuiz(QuizJson quizJson);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateQuizFromDto(Quiz source, @MappingTarget QuizEntity target);

    @Named("getQuestionsCount")
    default Integer getQuestionsCount(Set<Question> questions) {
        return questions != null ? questions.size() : 0;
    }

}
