package com.example.quizapp.json;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ScoreJson {

    @JsonView(Views.IdOnly.class)
    @Null(groups = ValidationGroups.Input.class)
    private Long id;

    @JsonView(Views.Input.class)
    @NotEmpty(groups = ValidationGroups.Input.class)
    @Length(min = 3, max = 50, groups = ValidationGroups.Input.class)
    private String username;

    @JsonView(Views.Output.class)
    @Min(value = 0, groups = ValidationGroups.Input.class)
    private int score;

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

