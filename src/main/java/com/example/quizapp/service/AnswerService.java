package com.example.quizapp.service;

import com.example.quizapp.entity.AnswerEntity;
import com.example.quizapp.mappers.AnswerMapper;
import com.example.quizapp.model.Answer;
import com.example.quizapp.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, AnswerMapper answerMapper) {
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
    }

    public Answer addAnswer(Answer answer) {
        AnswerEntity answerEntity = answerMapper.mapToAnswerEntity(answer);
        AnswerEntity savedAnswerEntity = answerRepository.save(answerEntity);
        return answerMapper.mapToAnswer(savedAnswerEntity);
    }

    public List<Answer> getAllAnswers() {
        List<AnswerEntity> answerEntities = answerRepository.findAll();
        return answerEntities.stream()
                .map(answerMapper::mapToAnswer)
                .toList();
    }

    public Optional<Answer> getAnswer(Long id) {
        return answerRepository.findById(id)
                .map(answerMapper::mapToAnswer);
    }

    public Answer updateAnswer(Long id, Answer answerDetails) {
        return answerRepository.findById(id)
                .map(existingAnswer -> {
                    existingAnswer.setAnswerText(answerDetails.getAnswerText());
                    existingAnswer.setCorrect(answerDetails.isCorrect());
                    AnswerEntity updatedAnswerEntity = answerRepository.save(existingAnswer);
                    return answerMapper.mapToAnswer(updatedAnswerEntity);
                })
                .orElseThrow(() -> new RuntimeException("Answer not found"));
    }

    public void deleteAnswer(Long id) {
        if (answerRepository.existsById(id)) {
            answerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Answer not found");
        }
    }
}
