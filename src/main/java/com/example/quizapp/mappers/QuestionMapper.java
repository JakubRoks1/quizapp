package com.example.quizapp.mappers;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.json.QuestionJson;
import com.example.quizapp.model.Question;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper
public interface QuestionMapper {

    QuestionEntity mapToQuestionEntity(Question question);

    Question mapToQuestion(QuestionEntity questionEntity);

    Set<Question> mapToQuestion(Set<QuestionEntity> questionEntities);

    QuestionJson mapToQuestionJson(Question question);

    Question mapToQuestion(QuestionJson questionJson);
}
