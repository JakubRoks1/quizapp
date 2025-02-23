package com.example.quizapp.controller;

import com.example.quizapp.exception.QuizAppException;
import com.example.quizapp.json.QuizQuestionJson;
import com.example.quizapp.service.QuizQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/QuizQuestionConnection")
public class QuizQuestionController {
    private final QuizQuestionService quizQuestionService;

    @Autowired
    public QuizQuestionController(QuizQuestionService quizQuestionService) {
        this.quizQuestionService = quizQuestionService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> connectQuizAndQuestion(@RequestBody QuizQuestionJson quizQuestionJson) {
        Long quizId = quizQuestionJson.getQuizId();
        Long questionId = quizQuestionJson.getQuestionId();

        try {
            quizQuestionService.addQuizQuestionConnection(quizId, questionId);
            return ResponseEntity.ok("Connected Quiz and Question successfully.");
        } catch (QuizAppException e) {
            return ResponseEntity.status(e.getCode()).body(e.getMessage());
        }
    }
}