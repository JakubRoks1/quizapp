package com.example.quizapp.json;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class QuestionJson {

    @Null(groups = ValidationGroups.Input.class)
    @JsonView({Views.IdOnly.class, Views.GetFull.class})
    private Long id;

    @NotEmpty
    @Length(min = 10, max = 255, groups = ValidationGroups.Input.class)
    @JsonView(Views.GetFull.class)
    private String questionText;

    @JsonView(Views.GetWithCount.class)
    private Integer answerCount; // For COUNT mode

    @JsonView(Views.GetShort.class)
    private String questionSummary;

    public interface Views {
        interface IdOnly {}
        interface GetFull {}
        interface Output {}
        interface GetShort {}
        interface GetWithCount {}
    }

    public record ValidationGroups() {
        public interface Input {};
        public interface Output {};
    }
}


