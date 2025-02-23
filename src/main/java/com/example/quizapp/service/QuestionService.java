package com.example.quizapp.service;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.exception.QuizAppException;
import com.example.quizapp.mappers.QuestionMapper;
import com.example.quizapp.model.Question;
import com.example.quizapp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    public Question addQuestion(Question question) {
        QuestionEntity questionEntity = questionMapper.mapToQuestionEntity(question);
        QuestionEntity savedQuestionEntity = questionRepository.save(questionEntity);
        return questionMapper.mapToQuestion(savedQuestionEntity);
    }

    public List<Question> getAllQuestions() {
        List<QuestionEntity> questionEntities = questionRepository.findAll();
        return questionEntities.stream()
                .map(questionMapper::mapToQuestion)
                .toList();
    }

    public Optional<Question> getQuestion(Long id) {
        return questionRepository.findById(id)
                .map(questionMapper::mapToQuestion);
    }

    public Question updateQuestion(Long id, Question questionDetails) {
        return questionRepository.findById(id)
                .map(existingQuestion -> {
                    existingQuestion.setQuestionText(questionDetails.getQuestionText());
                    QuestionEntity updatedQuestionEntity = questionRepository.save(existingQuestion);
                    return questionMapper.mapToQuestion(updatedQuestionEntity);
                })
                .orElseThrow(() -> new QuizAppException(404, "Question not found"));
    }

    public void deleteQuestion(Long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
        } else {
            throw new QuizAppException(404, "Question not found");
        }
    }

    public Question findById(Long id) {
        return questionRepository.findById(id)
                .map(questionMapper::mapToQuestion)
                .orElseThrow(() -> new RuntimeException("Question not found"));
    }

    public List<Question> getAllQuestionsWithFilteredProperties(List<String> fields) {
        List<Question> allQuestions = getAllQuestions();
        allQuestions.forEach(question -> filterFields(question, fields));
        return allQuestions;
    }

    private void filterFields(Question question, List<String> fields) {
        if (fields.contains("questionText")) {
            question.setQuestionText(null);
        }
        if (fields.contains("id")) {
            question.setId(null);
        }

    }

    public List<Question> getAllQuestionsWithFilterOutProperties(List<String> fields) {
        var allQuestions = getAllQuestions();
        allQuestions.forEach(question -> filterFields(question, fields));
        return allQuestions;
    }




}
