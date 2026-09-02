package com.example.quizapp.service;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.exception.ExceptionType;
import com.example.quizapp.mappers.QuestionMapper;
import com.example.quizapp.model.Question;
import com.example.quizapp.repository.QuestionRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    /** DO POPRAWY OPTIONAL JAKO TYP ZWRACANY -> powinien byc bez optionala */
    public Optional<Question> getQuestion(Long id) {
        val question = questionRepository.findById(id).map(questionMapper::mapToQuestion);

        if (question.isEmpty()) {
            throw ExceptionType.QUESTION_NOT_FOUND.getExceptionWithBody();
        }
        return question;
    }

    public Question updateQuestion(Long id, Question questionDetails) {
        return questionRepository.findById(id)
                .map(existingQuestion -> {
                    existingQuestion.setQuestionText(questionDetails.getQuestionText());
                    QuestionEntity updatedQuestionEntity = questionRepository.save(existingQuestion);
                    return questionMapper.mapToQuestion(updatedQuestionEntity);
                })
                .orElseThrow(() -> ExceptionType.QUESTION_NOT_FOUND.getException(true));
    }

    public void deleteQuestion(Long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
        } else {
            throw ExceptionType.QUESTION_NOT_FOUND.getException();
        }
    }

    public Question findById(Long id) {
        return questionRepository.findById(id)
                .map(questionMapper::mapToQuestion)
                .orElseThrow(ExceptionType.QUESTION_NOT_FOUND::getException);
    }

    public List<Question> getAllQuestionsWithFilteredProperties(List<String> fields) {
        List<QuestionEntity> questionEntities = questionRepository.findAll();
        List<Question> questions = questionEntities.stream()
                .map(questionMapper::mapToQuestion)
                .collect(Collectors.toList());

        System.out.println("Before filtering: " + questions);
        questions.forEach(question -> retainFields(question, fields));
        System.out.println("After filtering: " + questions);

        return questions;
    }

    private void retainFields(Question question, List<String> fields) {
        if (!fields.contains("questionText")) {
            question.setQuestionText(null);
        }
        if (!fields.contains("id")) {
            question.setId(null);
        }
    }

    public List<Question> getAllQuestionsWithFilterOutProperties(List<String> fields) {
        List<Question> allQuestions = getAllQuestions();
        allQuestions.forEach(question -> filterOutFields(question, fields));
        return allQuestions;
    }

    private void filterOutFields(Question question, List<String> fields) {
        if (fields.contains("questionText")) {
            question.setQuestionText(null);
        }
        if (fields.contains("id")) {
            question.setId(null);
        }
    }

    public void testException() {
        throw new IllegalArgumentException("Test AOP");
    }


}
