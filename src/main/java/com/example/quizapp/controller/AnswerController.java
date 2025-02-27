package com.example.quizapp.controller;

import com.example.quizapp.exception.ExceptionType;
import com.example.quizapp.exception.QuizAppException;
import com.example.quizapp.json.AnswerJson;
import com.example.quizapp.mappers.AnswerMapper;
import com.example.quizapp.model.Answer;
import com.example.quizapp.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerMapper answerMapper;

    @Autowired
    public AnswerController(AnswerService answerService, AnswerMapper answerMapper) {
        this.answerService = answerService;
        this.answerMapper = answerMapper;
    }

    @PostMapping
    public ResponseEntity<AnswerJson> createAnswer(@RequestBody AnswerJson answerJson) {
        Answer answer = answerMapper.mapToAnswer(answerJson);
        Answer savedAnswer = answerService.addAnswer(answer);
        AnswerJson responseJson = answerMapper.mapToAnswerJson(savedAnswer);
        return ResponseEntity.ok(responseJson);
    }

    @GetMapping
    public List<AnswerJson> getAllQuestions() {
        List<Answer> answers = answerService.getAllAnswers();
        return answers.stream()
                .map(answerMapper::mapToAnswerJson)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerJson> getAnswerById(@PathVariable Long id) {
        return answerService.getAnswer(id)
                .map(answerMapper::mapToAnswerJson)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnswerJson> updateAnswer(@PathVariable Long id, @RequestBody AnswerJson answerJson) {
        try {
            Answer updatedAnswer = answerMapper.mapToAnswer(answerJson);
            updatedAnswer.setId(id);
            Answer savedAnswer = answerService.updateAnswer(id, updatedAnswer);
            return ResponseEntity.ok(answerMapper.mapToAnswerJson(savedAnswer));
        } catch (QuizAppException e) {
            if (e.getExceptionType() == ExceptionType.ANSWER_NOT_FOUND) {
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAnswer(@PathVariable Long id) {
        return answerService.getAnswer(id)
                .map(answer -> {
                    answerService.deleteAnswer(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
