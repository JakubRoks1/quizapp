package com.example.quizapp.controller;

import com.example.quizapp.json.QuizJson;
import com.example.quizapp.mappers.QuizMapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.service.QuizService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    private final QuizService quizService;
    private final QuizMapper quizMapper;

    @Autowired
    public QuizController(QuizService quizService, QuizMapper quizMapper) {
        this.quizService = quizService;
        this.quizMapper = quizMapper;
    }

    @PostMapping
    @JsonView(QuizJson.Views.IdOnly.class)
    public ResponseEntity<QuizJson> createQuiz(@RequestBody @Validated({QuizJson.ValidationGroups.Input.class, Default.class}) QuizJson quizJson) {
        Quiz quiz = quizMapper.mapToQuiz(quizJson);
        Quiz savedQuiz = quizService.addQuiz(quiz);
        QuizJson responseJson = quizMapper.mapToQuizJson(savedQuiz);
        return ResponseEntity.ok(responseJson);
    }

    @GetMapping
    public List<QuizJson> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return quizzes.stream()
                .map(quizMapper::mapToQuizJson)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizJson> getQuizById(@PathVariable Long id) {
        return quizService.getQuiz(id)
                .map(quizMapper::mapToQuizJson)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PatchMapping("/{id}")
    public ResponseEntity<QuizJson> patchQuiz(@PathVariable Long id, @RequestBody /* walidacja TODO */ QuizJson quizJson) {
        try {
            return ResponseEntity.ok(quizMapper.mapToQuizJson(quizService.updateQuiz(id, quizMapper.mapToQuiz(quizJson))));
        } catch (RuntimeException e) {
            if ("nie-ma".equals(e.getMessage())) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteQuiz(@PathVariable Long id) {
        return ResponseEntity.status(quizService.deleteQuiz(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND).build();
    }
}
