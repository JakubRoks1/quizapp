package com.example.quizapp.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuizJson {

    @JsonView({Views.IdOnly.class, Views.GetFull.class, Views.GetShort.class})
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = ValidationGroups.Input.class)
    private Long id;

    @JsonView({Views.Input.class, Views.GetFull.class, Views.GetShort.class})
    @NotEmpty
    private String quizCategory;

    @JsonView({Views.Input.class, Views.GetFull.class, Views.GetShort.class})
    @Length(min = 20, max = 40, groups = ValidationGroups.Output.class)
    private String description;

    @JsonView(Views.GetFull.class)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<QuestionJson> questions;

    public interface Views {
        interface IdOnly {}
        interface Input {}
        interface GetFull extends QuestionJson.Views.GetFull {}
        interface GetShort {}
    }

    public record ValidationGroups() {
        public interface Input {};
        public interface Output {};
    }
}
