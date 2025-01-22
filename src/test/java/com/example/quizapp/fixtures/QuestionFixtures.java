package com.example.quizapp.fixtures;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.model.Question;
import static org.assertj.core.api.Assertions.*;

public class QuestionFixtures {
    public static QuestionEntity getQuestionEntity() {
        QuestionEntity question = new QuestionEntity();
        question.setId(1L);
        question.setQuestionText("Którą planetą jest Ziemia");
        return question;
    }

    public static Question getQuestion() {
        Question question = new Question();
        question.setId(1L);
        question.setQuestionText("Którą planetą jest Ziemia");
        return question;
    }

}
