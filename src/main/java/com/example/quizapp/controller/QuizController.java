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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequestMapping("/quizzes")
public class QuizController {
//    [-] 3) GET ALL na tej samej zasadzie - FULL / SHORT + a potem dodać COUNT [x]
//    [-] 4) DELETE (by ID) - usunąć można jedynie quiz który nie ma pytań [x]
//    [-] 5) "Podpinanie pytań" - metody w kotnrolerze w QuizController
//     /addQuestionToQuiz (id pytania, id quizu)
//     /removeQuestionFromQuiz (id pytania, id quizu)

//    walidacja czy pytanie istnieje, i czy quiz istnieje, czy pytanie jest już przypięte do quizu

//    6) PATCH quizu (wartości podstawowych)

//    przetestować quizService i Mappery

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
        var withQuestionsFlag = mode == FetchMode.FULL || mode == FetchMode.COUNT;

        return quizService.getQuiz(id, withQuestionsFlag)
            .map(quiz -> mode == FetchMode.COUNT ? quizMapper.mapToQuizJsonWithCount(quiz) : quizMapper.mapToQuizJson(quiz))
            .map(quizJson -> {
                var mjv = new MappingJacksonValue(quizJson);
                mjv.setSerializationView(FetchMode.getQuizJsonViewBasedOnFetchMode(mode));
                return mjv;
            })
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Praca domowa
    @GetMapping
    public List<MappingJacksonValue> getAllQuizzes(@RequestParam(defaultValue = "SHORT") FetchMode mode) {
        return quizService.getAllQuizzes()
                .stream()
                .map(quiz -> {
                    if (mode == FetchMode.COUNT) {
                        var quizJsonWithCount = quizMapper.mapToQuizJsonWithCount(quiz);
                        return new MappingJacksonValue(quizJsonWithCount);
                    } else {
                        var quizJson = quizMapper.mapToQuizJson(quiz);
                        var mjv = new MappingJacksonValue(quizJson);
                        mjv.setSerializationView(FetchMode.getQuizJsonViewBasedOnFetchMode(mode));
                        return mjv;
                    }
                })
                .collect(Collectors.toList());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuizSafely(id);
        return ResponseEntity
                .ok("Quiz successfully deleted");
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
