package com.example.quizapp.json;

import lombok.Data;

@Data
public class UserJson {
    private Long id;
    private String username;
    private String password;
    private String email;
}
