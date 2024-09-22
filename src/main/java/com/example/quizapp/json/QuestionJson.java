package com.example.quizapp.json;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class QuestionJson {

    @JsonView(Views.IdOnly.class)
    @Null(groups = ValidationGroups.Input.class)
    private Long id;

    @JsonView(Views.Input.class)
    @NotEmpty
    @Length(min = 10, max = 255, groups = ValidationGroups.Input.class)
    private String questionText;

    @JsonView(Views.Output.class)
    @NotEmpty(groups = ValidationGroups.Output.class)
    private String answer;

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


