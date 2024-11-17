package com.example.quizapp.controller;

import com.example.quizapp.json.FetchMode;
import com.example.quizapp.json.QuizFilteredJson;
import com.example.quizapp.json.QuizJson;
import com.example.quizapp.mappers.QuizMapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.service.QuizService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.transaction.Transactional;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<MappingJacksonValue> getQuizById(@PathVariable Long id, @RequestParam(defaultValue = "SHORT") FetchMode mode) {
        return quizService.getQuiz(id, FetchMode.FULL == mode)
            .map(quizMapper::mapToQuizJson)
            .map(quizJson -> {
                var mjv = new MappingJacksonValue(quizJson);
                mjv.setSerializationView(mode == FetchMode.FULL ? QuizJson.Views.GetFull.class : QuizJson.Views.GetShort.class);
                return mjv;
            })
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Praca domowa
    @GetMapping
    public List<QuizJson> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return quizzes.stream()
                .map(quizMapper::mapToQuizJson)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteQuiz(@PathVariable Long id) {
        return ResponseEntity.status(quizService.deleteQuiz(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND).build();
    }

    // zad.2) też wybór metody geta w zależności od pola
    @Transactional
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @GetMapping("/GetByFields")
    public ResponseEntity<List<QuizJson>> getSpecified(@RequestParam List<String> fields) {
        List<Quiz> quizzes = quizService.getAllQuizzesWithFilterOutProperties(fields);
        List<QuizJson> quizJsons = quizzes.stream()
                .map(quizMapper::mapToQuizJson)
                .toList();

        // do zrobienia podejście z nową dedykowaną klasą w jsonmodelu
        return ResponseEntity.ok(quizJsons);
    }

    @Transactional
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @GetMapping("/GetByFilteredFields")
    public ResponseEntity<List<QuizFilteredJson>> getSpecifiedFiltered(@RequestParam List<String> fields) {
        List<Quiz> quizzes = quizService.getAllQuizzesWithFilterOutProperties(fields);

        List<QuizFilteredJson> quizFilteredJsons = quizzes.stream()
                .map(quizMapper::mapToQuizFilteredJson)
                .toList();

        return ResponseEntity.ok(quizFilteredJsons);
    }
}
