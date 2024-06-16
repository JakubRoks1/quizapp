package com.example.quizapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// 1) w jednym endpoincie get Question
// questions/{id} - to zwraca to pytania o podanym id
// questions - to zwraca wszystkie pytania
// 2) poczyscic poma [X]
// 3) zmienić posta Usera by używać requestParam - http://localhost:8080/users/?id=1&username=Mateusz&password=abc&email=abc@wp.pl (email Opcjonalny/null) i defaultValue
// 4) get na usery za pomoca response Entity jak nie ma nic to kod 204 jak jest to kod 200
@SpringBootApplication
public class QuizAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizAppApplication.class, args);
    }

}
