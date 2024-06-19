package com.example.quizapp.mappers;

import com.example.quizapp.entity.ScoreEntity;
import com.example.quizapp.json.ScoreJson;
import com.example.quizapp.model.Score;
import org.mapstruct.Mapper;

@Mapper
public interface ScoreMapper {

    ScoreEntity mapToScoreEntity(Score score);

    Score mapToScore(ScoreEntity scoreEntity);

    ScoreJson mapToScoreJson(Score score);

}
