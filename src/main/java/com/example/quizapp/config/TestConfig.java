package com.example.quizapp.config;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.entity.QuizEntity;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestConfig {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public TestConfig(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    @PostConstruct
    public void a() {

        var quizEntity = new QuizEntity();
        quizEntity.setDescription("Test your knowledge of world capitals.");
        quizEntity.setQuizCategory("Geography");
        quizRepository.save(quizEntity);

        var questionEntity = new QuestionEntity();
        questionEntity.setQuestionText("What is the capital of Canada?");
        questionRepository.save(questionEntity);

//        quizEntity.setQuestions(Set.of(questionEntity));

    }

    public static QuizEntity testowaEncja() {
        var quizEntity = new QuizEntity();
        quizEntity.setDescription("Test your knowledge of world capitals.");
        quizEntity.setQuizCategory("Geography");

        var questionEntity = new QuestionEntity();
        questionEntity.setQuestionText("What is the capital of Canada?");

//        quizEntity.setQuestions(Set.of(questionEntity));

        return quizEntity;
    }


}
