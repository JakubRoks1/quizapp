package com.example.quizapp.service;

import com.example.quizapp.mappers.ScoreMapper;
import com.example.quizapp.model.Score;
import com.example.quizapp.repository.ScoreRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final ScoreMapper scoreMapper;

    @PostConstruct
    void pc() {
        save(new Score(1L, 20, "Mateusz"));
        save(new Score(2L, 30, "Adam"));
    }

    public void save(Score score) {
        var scoreEntity = scoreMapper.mapToScoreEntity(score);
        scoreRepository.save(scoreEntity);
    }

    public List<Score> getAll() {


        val all = scoreRepository.findAll();
        return all.stream().map(scoreMapper::mapToScore).toList();
    }

//    private ScoreEntity mapToScoreEntity(Score score) {
//        var scoreEntity = new ScoreEntity();
//        scoreEntity.setId(score.getId());
//        scoreEntity.setScore(score.getScore());
//        scoreEntity.setUsername(score.getUsername());
//        return scoreEntity;
//    }

//    private Score mapToScore(ScoreEntity scoreEntity) {
//        var score = new Score(scoreEntity.getId(),
//            scoreEntity.getScore(),
//            scoreEntity.getUsername());
//        return score;
//    }
}
