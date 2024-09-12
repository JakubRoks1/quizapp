package com.example.quizapp.json;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class QuizJsonViewExample {
    @JsonView(Views.Output.class)
    private Long id;
    @JsonView({Views.Input.class, Views.Output.class})
    private String quizCategory;
    @JsonView({Views.Input.class})
    private String description;

    public static class Views {
        public static class Input {}
        public static class Output {}
    }
}
