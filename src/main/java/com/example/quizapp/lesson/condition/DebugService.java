package com.example.quizapp.lesson.condition;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("debug")
@Service
public class DebugService {

    public void debug() {
        System.out.println("------------> debug");
    }
}
