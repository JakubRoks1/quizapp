package com.example.quizapp.json;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class QuestionJson {

    @Null(groups = ValidationGroups.Input.class)
    private Long id;

    @NotEmpty
    @Length(min = 10, max = 255, groups = ValidationGroups.Input.class)
    private String questionText;

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


