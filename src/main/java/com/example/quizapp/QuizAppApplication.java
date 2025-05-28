package com.example.quizapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Aplikacja potrafi załadować quiz, stworzyć pytania do quiz (POSTami)
 * Usuńmy na razie odpowiedzi - wielokrotny wybór, ma być jedna odpoweidź - przykład Trzecia planeta od słonca? Ziemia
 * Skonfiguruj aplikację tak, aby dodane quizy były pernamentne - tzn. po restarcie aplikacji dane dalej istniały
 * usuwanie pytan / usuwanie quizów (+ edycja)
 *
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = QuizAppApplication.class, // lub pozostaw domyślnie
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.REGEX,
        pattern = "com\\.example\\.quizapp\\.lesson\\..*"
    ))
@SpringBootConfiguration
public class QuizAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizAppApplication.class, args);
    }
}
