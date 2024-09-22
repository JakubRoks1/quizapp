package com.example.quizapp.json;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserJson {

    @JsonView(Views.IdOnly.class)
    @Null(groups = ValidationGroups.Input.class)
    private Long id;

    @JsonView(Views.Input.class)
    @NotEmpty(groups = ValidationGroups.Input.class)
    @Length(min = 3, max = 50, groups = ValidationGroups.Input.class)
    private String username;

    @JsonView(Views.Input.class)
    @NotEmpty(groups = ValidationGroups.Input.class)
    @Length(min = 8, max = 100, groups = ValidationGroups.Input.class)
    private String password;

    @JsonView(Views.Input.class)
    @NotEmpty(groups = ValidationGroups.Input.class)
    @Email(groups = ValidationGroups.Input.class)
    private String email;

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

