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
/**
 * 1) Porządek z JSONView dla QuizJson - getAll (wariant z pytaniami, i bez pytań)
 * 2) Dodatkowy get/getAll (bez widoków), wyświetla tylko pola podane w parametrze.
 * 3) Rozszerz testConfig - wiecej przykladow - quiz bez pytan, quiz z pytaniem, quiz z pytaniami, kilka pytan niepodpietych [x]
 */
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

    // wszystkie z pytaniami i bez pytań (wariant)
    @GetMapping
    public List<QuizJson> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return quizzes.stream()
                .map(quizMapper::mapToQuizJson)
                .toList();
    }

    @GetMapping(value = "/{id}", params = "full")
    @JsonView(QuizJson.Views.GetFull.class)
    public ResponseEntity<QuizJson> getQuizById(@PathVariable Long id) {
        return quizService.getQuiz(id)
                .map(quizMapper::mapToQuizJson)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @JsonView(QuizJson.Views.GetShort.class)
    public ResponseEntity<QuizJson> getQuizByIdShort(@PathVariable Long id) {
        return getQuizById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteQuiz(@PathVariable Long id) {
        return ResponseEntity.status(quizService.deleteQuiz(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND).build();
    }

    // zad.2) też wybór metody geta w zależności od pola
    @GetMapping("/cos")
    public ResponseEntity<List<QuizJson>> getSpecified(@RequestParam List<String> fields) {
        List<Quiz> quizzes = quizService.getAllQuizzesWithFilterOutProperties(fields);
        List<QuizJson> quizJsons = quizzes.stream()
                .map(quizMapper::mapToQuizJson)
                .toList();

        // do zrobienia podejście z nową dedykowaną klasą w jsonmodelu
        return ResponseEntity.ok(quizJsons);
    }
}
