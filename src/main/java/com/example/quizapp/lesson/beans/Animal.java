package com.example.quizapp.lesson.beans;

import org.springframework.beans.factory.annotation.Value;

public record Animal(String type, String name) {
    public Animal(@Value("dog") String type, @Value("Reksio") String name) {
        this.type = type;
        this.name = name;
    }

    // DOG, Reksio
    // CAT, Mruczek
}
