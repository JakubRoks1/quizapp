package com.example.quizapp.service;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.entity.QuizEntity;
import com.example.quizapp.exception.ExceptionType;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizQuestionService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public QuizQuestionService(QuizRepository quizRepository,
                               QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    // Method to create the connection using IDs
//    @Transactional
    public void addQuizQuestionConnection(Long quizId, Long questionId) {
        QuizEntity quiz = quizRepository.findById(quizId)
                .orElseThrow(ExceptionType.QUIZ_NOT_FOUND::getException);
        QuestionEntity question = questionRepository.findById(questionId)
                .orElseThrow(ExceptionType.QUESTION_NOT_FOUND::getException);

        quiz.getQuestions().add(question);
        quizRepository.save(quiz);
    }
}

