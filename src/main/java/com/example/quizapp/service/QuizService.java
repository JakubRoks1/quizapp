package com.example.quizapp.service;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.entity.QuizEntity;
import com.example.quizapp.mappers.QuizMapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;

    private final QuestionRepository questionRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository, QuizMapper quizMapper, QuestionRepository questionRepository,
                       EntityManager entityManager) {
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public void addQuestionToQuiz(Long quizId, Long questionId) {
        QuizEntity quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        Hibernate.initialize(quiz.getQuestions());

        QuestionEntity question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        quiz.getQuestions().add(question);
        quizRepository.save(quiz);
    }

    public Quiz addQuiz(Quiz quiz) {
        QuizEntity quizEntity = quizMapper.mapToQuizEntity(quiz);
        QuizEntity savedQuizEntity = quizRepository.save(quizEntity);
        return quizMapper.mapToQuiz(savedQuizEntity);
    }

    public List<Quiz> getAllQuizzes() {
        List<QuizEntity> quizEntities = quizRepository.findAll();
        return quizEntities.stream()
                .map(quizMapper::mapToQuiz)
                .toList();
    }

    public Optional<Quiz> getQuiz(Long id) {
        return quizRepository.findById(id)
                .map(quizMapper::mapToQuiz);
    }

    public Quiz updateQuiz(Long id, Quiz quizDetails) {
        return quizRepository.findById(id)
                .map(existingQuiz -> {
                    existingQuiz.setQuizCategory(quizDetails.getQuizCategory());
                    existingQuiz.setDescription(quizDetails.getDescription());
                    QuizEntity updatedQuizEntity = quizRepository.save(existingQuiz);
                    return quizMapper.mapToQuiz(updatedQuizEntity);
                })
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    public void deleteQuiz(Long id) {
        if (quizRepository.existsById(id)) {
            quizRepository.deleteById(id);
        } else {
            throw new RuntimeException("Quiz not found");
        }
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .map(quizMapper::mapToQuiz)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

}

