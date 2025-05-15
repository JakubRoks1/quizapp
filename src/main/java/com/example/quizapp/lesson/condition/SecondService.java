package com.example.quizapp.lesson.condition;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("abc")
public class SecondService implements Calculator{


    public int calc() {
        return 2;
    }

    @PostConstruct
    public void a() {
        System.out.println("------------");
        System.out.println("------------");
        System.out.println(SecondService.class + " initialized");
        System.out.println("------------");
        System.out.println("------------");
    }
}
