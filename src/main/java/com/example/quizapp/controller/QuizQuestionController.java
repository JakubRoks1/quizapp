package com.example.quizapp.controller;

import com.example.quizapp.entity.QuizQuestionEntity;
import com.example.quizapp.json.QuizQuestionJson;
import com.example.quizapp.mappers.QuizMapper;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.service.QuestionService;
import com.example.quizapp.service.QuizQuestionService;
import com.example.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/QuizQuestionConnection")
public class QuizQuestionController {

    private final QuizService quizService;
    private final QuestionService questionService;

    private final QuizQuestionService quizQuestionService;

    @Autowired
    public QuizQuestionController(QuizService quizService, QuestionService questionService, QuizQuestionService quizQuestionService) {
        this.quizService = quizService;
        this.questionService = questionService;
        this.quizQuestionService = quizQuestionService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> connectQuizAndQuestion(@RequestBody QuizQuestionJson quizQuestionJson) {
        Long quizId = quizQuestionJson.getQuizId();
        Long questionId = quizQuestionJson.getQuestionId();

        try {
            quizQuestionService.addQuizQuestionConnection(quizId, questionId);
            return ResponseEntity.ok("Connected Quiz and Question successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}