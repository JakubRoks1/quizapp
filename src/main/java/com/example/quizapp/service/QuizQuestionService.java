package com.example.quizapp.service;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.entity.QuizEntity;
import com.example.quizapp.entity.QuizQuestionEntity;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizQuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizQuestionService {

    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public QuizQuestionService(QuizQuestionRepository quizQuestionRepository,
                               QuizRepository quizRepository,
                               QuestionRepository questionRepository) {
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    // Method to create the connection using IDs
    public void addQuizQuestionConnection(Long quizId, Long questionId) {
        QuizEntity quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        QuestionEntity question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        QuizQuestionEntity quizQuestion = new QuizQuestionEntity();
        quizQuestion.setQuiz(quiz);
        quizQuestion.setQuestion(question);

        quizQuestionRepository.save(quizQuestion);
    }
}

