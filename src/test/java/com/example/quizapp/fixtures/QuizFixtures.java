package com.example.quizapp.fixtures;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.entity.QuizEntity;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import lombok.val;

import java.util.Set;

// [PRACA DOMOWA z 9/01/2025]
// przygotować metody wytwórcze (factory methods), które zwracają obiekty do testów, pozamieniać w metodach Quiz i Question
// używamy AssertJ
public class QuizFixtures {

    public static QuizEntity getQuizEntity() {
        QuestionEntity givenQuestion = new QuestionEntity();
        givenQuestion.setId(1L);
        givenQuestion.setQuestionText("Którą planetą jest Ziemia");
        QuizEntity givenQuiz = new QuizEntity();
        givenQuiz.setId(1L);
        givenQuiz.setQuizCategory("Astronomia");
        givenQuiz.setDescription("Pytania o planetach i księżycach");
        val questionSet = Set.of(givenQuestion);
        givenQuiz.setQuestions(questionSet);
        return givenQuiz;
    }

    public static Quiz getQuiz() {
        Question givenQuestion = new Question();
        givenQuestion.setId(1L);
        givenQuestion.setQuestionText("Którą planetą jest Ziemia");
        Quiz givenQuiz = new Quiz();
        givenQuiz.setId(1L);
        givenQuiz.setQuizCategory("Astronomia");
        givenQuiz.setDescription("Pytania o planetach i księżycach");
        val questionSet = Set.of(givenQuestion);
        givenQuiz.setQuestions(questionSet);
        return givenQuiz;
    }

    public static Quiz givenQuizWithoutQuestions() {
        var quiz = getQuiz();
        quiz.setQuestions(null);
        return quiz;
    }
}
