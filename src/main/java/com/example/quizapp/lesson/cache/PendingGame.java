package com.example.quizapp.lesson.cache;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.*;

@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
public final class PendingGame {
    private final UUID id;
    private final String username;
    private final List<Person> persons;
    private final List<String> answers;

    public PendingGame(UUID id, String username, List<Person> persons) {
        this.id = id;
        this.username = username;
        this.persons = persons;
        this.answers = new ArrayList<>();
    }

    public Person nextPerson() {
        return persons.get(answers.size());
    }

    public boolean submitAnswer(String answer) {
        answers.add(answer);
        return answers.size() < persons.size();
    }

    public int getScore() {
        int score = 0;
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).equalsIgnoreCase(persons.get(i).name())) {
                score++;
            }
        }
        return score;
    }
}
