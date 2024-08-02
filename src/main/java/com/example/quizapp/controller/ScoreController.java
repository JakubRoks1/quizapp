package com.example.quizapp.controller;

import com.example.quizapp.json.ScoreJson;
import com.example.quizapp.mappers.ScoreMapper;
import com.example.quizapp.service.ScoreService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/scores")
public class ScoreController {

    private final ScoreService scoreService;
    private final ScoreMapper scoreMapper;

    @Autowired
    public ScoreController(ScoreService scoreService, ScoreMapper scoreMapper) {
        this.scoreService = scoreService;
        this.scoreMapper = scoreMapper;
    }

    @PostMapping
    public ResponseEntity<ScoreJson> createScore(@RequestBody ScoreJson scoreJson) {
        ScoreJson responseJson = scoreService.addScore(scoreJson);
        return ResponseEntity.ok(responseJson);
    }

    @GetMapping
    public List<ScoreJson> getAllScores() {
        return scoreService.getAllScores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScoreJson> getScoreById(@PathVariable Long id) {
        return scoreService.getScore(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScoreJson> updateScore(@PathVariable Long id, @RequestBody ScoreJson scoreJson) {
        try {
            ScoreJson updatedScoreJson = scoreService.updateScore(id, scoreJson);
            return ResponseEntity.ok(updatedScoreJson);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteScore(@PathVariable Long id) {
        return scoreService.getScore(id)
                .map(score -> {
                    scoreService.deleteScore(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
