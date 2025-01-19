package com.example.quizapp.fixtures;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.model.Question;

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

    public static QuestionEntity getQuestionEntityWithCustomId(Long id) {
        QuestionEntity question = getQuestionEntity();
        question.setId(id);
        return question;
    }

    public static Question getQuestionWithCustomId(Long id) {
        Question question = getQuestion();
        question.setId(id);
        return question;
    }

    public static QuestionEntity getQuestionEntityWithCustomText(String text) {
        QuestionEntity question = getQuestionEntity();
        question.setQuestionText(text);
        return question;
    }

    public static Question getQuestionWithCustomText(String text) {
        Question question = getQuestion();
        question.setQuestionText(text);
        return question;
    }
}
