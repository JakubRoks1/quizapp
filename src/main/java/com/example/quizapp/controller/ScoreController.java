package com.example.quizapp.controller;

import com.example.quizapp.json.ScoreJson;
import com.example.quizapp.mappers.ScoreMapper;
import com.example.quizapp.service.ScoreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/scores")
@AllArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;
    private final ScoreMapper scoreMapper;

    @GetMapping
    public List<ScoreJson> getAllScores() {
        return scoreService.getAll().stream().map(scoreMapper::mapToScoreJson).toList();
    }

}
