package com.example.quizapp.json;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuizQuestionJson {
    private Long quizId;
    private Long questionId;
}
