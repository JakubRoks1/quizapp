package com.example.quizapp.mappers;

import com.example.quizapp.entity.AnswerEntity;
import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.json.AnswerJson;
import com.example.quizapp.json.QuestionJson;
import com.example.quizapp.model.Answer;
import com.example.quizapp.model.Question;
import org.mapstruct.Mapper;

@Mapper
public interface AnswerMapper {

    AnswerEntity mapToAnswerEntity(Answer answer);

    Answer mapToAnswer(AnswerEntity answerEntity);

    AnswerJson mapToAnswerJson(Answer answer);

    Answer mapToAnswer(AnswerJson answerJson);
}
