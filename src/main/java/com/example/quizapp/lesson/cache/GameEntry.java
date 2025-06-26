package com.example.quizapp.lesson.cache;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GameEntry(UUID id, String question, List<Person> askedPersons, List<String> answers, Integer score) {


    public GameEntry(UUID id, String question) {
        this(id, question, null, null, null);
    }

    public GameEntry(UUID id, List<Person> askedPersons, List<String> answers, Integer score) {
        this(id, null, askedPersons, answers, score);
    }
}
