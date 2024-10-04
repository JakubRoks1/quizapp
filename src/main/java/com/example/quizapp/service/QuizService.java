package com.example.quizapp.service;

import com.example.quizapp.config.TestConfig;
import com.example.quizapp.entity.QuizEntity;
import com.example.quizapp.mappers.QuizMapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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
        return Optional.ofNullable(quizMapper.mapToQuiz(TestConfig.testowaEncja()));

//        return quizRepository.findById(id)
//                .map(quizMapper::mapToQuiz);
    }

    public Quiz updateQuiz(Long id, Quiz quizOverrideFields) {
        var byId = quizRepository.findById(id);
        if (byId.isEmpty()) {
            throw new RuntimeException("nie-ma");
        }

        var existing = byId.get();

        quizMapper.updateQuizFromDto(quizOverrideFields, existing);

        var saved = quizRepository.save(existing);
        return quizMapper.mapToQuiz(saved);
    }

    @Transactional
    public boolean deleteQuiz(Long id) {
        var byId = quizRepository.findById(id);

        if (byId.isPresent()) {
            quizRepository.delete(byId.get());
            return true;
        } else {
            return false;
        }
    }

}

