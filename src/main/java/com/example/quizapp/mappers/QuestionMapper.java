package com.example.quizapp.mappers;

import com.example.quizapp.json.QuestionJson;
import com.example.quizapp.entity.QuestionEntity;

public interface QuestionMapper {

    com.example.quizapp.entity.QuestionEntity mapToQuestionEntity(QuestionEntity question);

    QuestionEntity mapToQuestion(com.example.quizapp.entity.QuestionEntity questionEntity);

    QuestionJson mapToQuestionJson(QuestionEntity question);
}
