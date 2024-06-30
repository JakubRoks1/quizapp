package com.example.quizapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// Dla encji User i Question wystawic CRUD endpointy - create (z body), read (z parametrem id, readAll), update (z body i request parametrem id), delete (z parametrem id) [X]
// Użyć osobnych klas dla modeli JSON Entity i ApplicationLevel (uzyc Mapstructa)
// service testy
// walidacje
@SpringBootApplication
public class QuizAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizAppApplication.class, args);
    }

}
