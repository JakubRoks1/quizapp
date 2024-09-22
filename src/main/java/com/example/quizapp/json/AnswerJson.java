package com.example.quizapp.json;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class AnswerJson {

    @JsonView(Views.IdOnly.class)
    @Null(groups = ValidationGroups.Input.class)
    private Long id;

    @JsonView(Views.Input.class)
    @NotEmpty
    private String answerText;

    @JsonView(Views.Output.class)
    private boolean isCorrect;

    @JsonView(Views.Input.class)
    @NotNull(groups = ValidationGroups.Input.class)
    @Min(value = 1, groups = ValidationGroups.Input.class)
    private Long questionId;

    public interface Views {
        interface IdOnly {}
        interface Input {}
        interface Output {}
    }

    public record ValidationGroups() {
        public interface Input {};
        public interface Output {};
    }
}

