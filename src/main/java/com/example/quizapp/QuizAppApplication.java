package com.example.quizapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// praca domowa wykonac CRUD dla wszysztkich modeli (C-Create, U-Update, D-Delete, R-Read)
// RestController (orzymuje UserJSON) -> Service (otrzymuje User) -> Repository (otrzymuje UserEntity) -- zapis zap. UserEntity -- > Service (zwaraca User) ---> RestController zwraca JSONa
@SpringBootApplication
public class QuizAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizAppApplication.class, args);
    }

}
