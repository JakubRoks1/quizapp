package com.example.quizapp;

import com.example.quizapp.config.AppConfig;
import com.example.quizapp.model.User;
import jakarta.annotation.PostConstruct;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizAppApplication {

    @Autowired
    private User user;

    @Autowired
    private AppConfig appConfig;

    public static void main(String[] args) {
        SpringApplication.run(QuizAppApplication.class, args);
    }

    @PostConstruct
    public void pc() {
        System.out.println(user + " -------------");

        val user1 = appConfig.user1();
        val user2 = appConfig.user1();

        System.out.println(user == user1); // true
        System.out.println(user1 == user2); // false
        System.out.println(user2 == user); // true

    }
}
