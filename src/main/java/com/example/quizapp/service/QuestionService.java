package com.example.quizapp.service;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public QuestionEntity addQuestion(QuestionEntity question) {
        return questionRepository.save(question);
    }

    public List<QuestionEntity> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<QuestionEntity> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

//    public QuestionEntity updateQuestion(Long id, QuestionEntity questionDetails) {
//        return questionRepository.findById(id)
//                .map(question -> {
//                    question.setQuestionText(questionDetails.getQuestionText());
//                    question.setAnswer(questionDetails.getAnswer());
//                    return questionRepository.save(question);
//                }).orElseThrow(() -> new RuntimeException("Question not found"));
//    }

    public void deleteQuestion(Long id) {
        questionRepository.findById(id)
                .ifPresent(questionRepository::delete);
    }
}
