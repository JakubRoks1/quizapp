package com.example.quizapp.config;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.entity.QuizEntity;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

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

        // Quiz without questions
        var quizWithoutQuestions = new QuizEntity();
        quizWithoutQuestions.setDescription("Quiz without any questions.");
        quizWithoutQuestions.setQuizCategory("General Knowledge");
        quizRepository.save(quizWithoutQuestions);

        // Quiz with a single question
        var quizWithOneQuestion = new QuizEntity();
        quizWithOneQuestion.setDescription("Quiz with a single question.");
        quizWithOneQuestion.setQuizCategory("Science");

        var singleQuestion = new QuestionEntity();
        singleQuestion.setQuestionText("What is the boiling point of water?");
        questionRepository.save(singleQuestion);

        quizWithOneQuestion.setQuestions(Set.of(singleQuestion));
        quizRepository.save(quizWithOneQuestion);

        // Quiz with multiple questions
        var quizWithMultipleQuestions = new QuizEntity();
        quizWithMultipleQuestions.setDescription("Quiz with multiple questions.");
        quizWithMultipleQuestions.setQuizCategory("History");

        var question1 = new QuestionEntity();
        question1.setQuestionText("Who was the first President of the United States?");
        questionRepository.save(question1);

        var question2 = new QuestionEntity();
        question2.setQuestionText("In what year did the Titanic sink?");
        questionRepository.save(question2);

        quizWithMultipleQuestions.setQuestions(Set.of(question1, question2));
        quizRepository.save(quizWithMultipleQuestions);

        // Several unassigned questions
        var unassignedQuestion1 = new QuestionEntity();
        unassignedQuestion1.setQuestionText("What is the capital of France?");
        questionRepository.save(unassignedQuestion1);

        var unassignedQuestion2 = new QuestionEntity();
        unassignedQuestion2.setQuestionText("How many seas are there on Earth?");
        questionRepository.save(unassignedQuestion2);

        var unassignedQuestion3 = new QuestionEntity();
        unassignedQuestion3.setQuestionText("How many continents are there on Earth?");
        questionRepository.save(unassignedQuestion3);

        //quizEntity.setQuestions(Set.of(questionEntity));

    }

    public static QuizEntity testowaEncja() {
        var quizEntity = new QuizEntity();
        quizEntity.setDescription("Test your knowledge of world capitals.");
        quizEntity.setQuizCategory("Geography");

        var questionEntity = new QuestionEntity();
        questionEntity.setQuestionText("What is the capital of Canada?");

        //quizEntity.setQuestions(Set.of(questionEntity));

        return quizEntity;
    }


}
