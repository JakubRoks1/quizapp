package com.example.quizapp.lesson.condition;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StarterCondition {

    @Autowired
    private FinalService finalService;

    public static void main(String[] args) {
        SpringApplication.run(StarterCondition.class, args);
    }

    @PostConstruct
    public void pc() {
        System.out.println("Result here:");
        System.out.println(finalService.caluclate());
    }
}
