package com.example.quizapp.lesson.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewCacheApp {

    public static void main(String[] args) {
        SpringApplication.run(NewCacheApp.class, args);
    }
}

/**
 http://localhost:8080/game/start
 {
 "size": 2
 }

 POST http://localhost:8080/game/answer

 {
 "id": "d53d7c5f-12cc-457d-94b2-be5e2f565933",
 "answer": "Jill"
 }

 */