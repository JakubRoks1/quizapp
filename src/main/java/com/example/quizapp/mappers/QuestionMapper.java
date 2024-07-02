package com.example.quizapp.mappers;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.entity.ScoreEntity;
import com.example.quizapp.json.QuestionJson;
import com.example.quizapp.json.ScoreJson;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Score;

public interface QuestionMapper {

    QuestionEntity mapToQuestionEntity(Question question);

    Question mapToQuestion(QuestionEntity questionEntity);

    QuestionJson mapToQuestionJson(Question question);
}
