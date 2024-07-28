package com.example.quizapp.service;

import com.example.quizapp.json.AnswerJson;
import com.example.quizapp.mappers.AnswerMapper;
import com.example.quizapp.model.Answer;
import com.example.quizapp.repository.AnswerRepository;
import com.example.quizapp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

//    public Answer convertToEntity(AnswerJson answerJson) {
//        return answerMapper.mapToAnswer(answerJson);
//    }
//
//    public AnswerJson convertToDto(Answer answer) {
//        return answerMapper.mapToAnswerJson(answer);
//    }
}
