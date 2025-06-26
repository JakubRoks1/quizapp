package com.example.quizapp.lesson.cache;

import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class GameService {

    private final static List<Person> AVAILABLE_PEOPLE = List.of(
        new Person(1L, "John"),
        new Person(2L, "Jane"),
        new Person(3L, "Jack"),
        new Person(4L, "Jill")
    );

    private PendingGame pendingGame = null;

    public GameEntry startGame(GameInput gameInput) {
        if (pendingGame != null) {
            throw new IllegalStateException("Game already started");
        }

        val people = new ArrayList<>(AVAILABLE_PEOPLE);
        Collections.shuffle(people);
        val list = people.stream().limit(gameInput.size()).toList();
        pendingGame = new PendingGame(UUID.randomUUID(), list);

        return new GameEntry(pendingGame.id(), "Podaj imię osoby nr " + pendingGame.nextPerson().id());
    }

    public GameEntry submitAnswer(AnswerInput answerInput) {
        if (pendingGame == null || !pendingGame.id().equals(answerInput.id())) {
            throw new IllegalStateException("Game not started or invalid id");
        }

        val isOngoing = pendingGame.submitAnswer(answerInput.answer());
        if (isOngoing) {
            return new GameEntry(pendingGame.id(), "Podaj imię osoby nr " + pendingGame.nextPerson().id());
        } else {
            val result = new GameEntry(pendingGame.id(), pendingGame.persons(), pendingGame.answers(), pendingGame.getScore());
            pendingGame = null;
            return result;
        }
    }

}
