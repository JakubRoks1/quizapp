package com.example.quizapp.lesson.cache;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.*;

@Getter
@Accessors(fluent = true)
public final class PendingGame {
    private final UUID id;
    private final List<Person> persons;
    private final List<String> answers;

    public PendingGame(UUID id, List<Person> persons) {
        this.id = id;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PendingGame) obj;
        return Objects.equals(this.id, that.id) &&
            Objects.equals(this.persons, that.persons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, persons);
    }

    @Override
    public String toString() {
        return "PendingGame[" +
            "id=" + id + ", " +
            "persons=" + persons + ']';
    }
}
