package com.example.quizapp.service;

import com.example.quizapp.entity.ScoreEntity;
import com.example.quizapp.json.ScoreJson;
import com.example.quizapp.mappers.ScoreMapper;
import com.example.quizapp.model.Score;
import com.example.quizapp.repository.ScoreRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final ScoreMapper scoreMapper;

    @Autowired
    public ScoreService(ScoreRepository scoreRepository, ScoreMapper scoreMapper) {
        this.scoreRepository = scoreRepository;
        this.scoreMapper = scoreMapper;
    }

    public ScoreJson addScore(ScoreJson scoreJson) {
        Score score = scoreMapper.mapToScore(scoreJson);
        ScoreEntity scoreEntity = scoreMapper.mapToScoreEntity(score);
        ScoreEntity savedScoreEntity = scoreRepository.save(scoreEntity);
        Score savedScore = scoreMapper.mapToScore(savedScoreEntity);
        return scoreMapper.mapToScoreJson(savedScore);
    }

    public List<ScoreJson> getAllScores() {
        List<ScoreEntity> scoreEntities = scoreRepository.findAll();
        return scoreEntities.stream()
                .map(scoreMapper::mapToScore)
                .map(scoreMapper::mapToScoreJson)
                .collect(Collectors.toList());
    }

    public Optional<ScoreJson> getScore(Long id) {
        return scoreRepository.findById(id)
                .map(scoreMapper::mapToScore)
                .map(scoreMapper::mapToScoreJson);
    }

    public ScoreJson updateScore(Long id, ScoreJson scoreJson) {
        return scoreRepository.findById(id)
                .map(existingScore -> {
                    existingScore.setScore(scoreJson.getScore());
                    ScoreEntity updatedScoreEntity = scoreRepository.save(existingScore);
                    Score updatedScore = scoreMapper.mapToScore(updatedScoreEntity);
                    return scoreMapper.mapToScoreJson(updatedScore);
                })
                .orElseThrow(() -> new RuntimeException("Score not found"));
    }

    public void deleteScore(Long id) {
        if (scoreRepository.existsById(id)) {
            scoreRepository.deleteById(id);
        } else {
            throw new RuntimeException("Score not found");
        }
    }
}
