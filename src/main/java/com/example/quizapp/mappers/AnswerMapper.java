package com.example.quizapp.mappers;

import com.example.quizapp.entity.AnswerEntity;
import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.json.AnswerJson;
import com.example.quizapp.model.Answer;
import org.mapstruct.Mapper;

@Mapper
public interface AnswerMapper {

    com.example.quizapp.entity.AnswerEntity mapToAnswerEntity(AnswerEntity answer);
    Answer mapToAnswer(AnswerJson answerJson);

    AnswerJson mapToAnswerJson(Answer answer);
}
