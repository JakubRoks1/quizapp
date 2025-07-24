package com.example.quizapp.lesson.cache;

import lombok.SneakyThrows;
import lombok.val;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    private final static List<Person> AVAILABLE_PEOPLE = List.of(
        new Person(1L, "John"),
        new Person(2L, "Jane"),
        new Person(3L, "Jack"),
        new Person(4L, "Jill")
    );

    private final Map<String, PendingGame> pendingGamePerUserMap = new HashMap<>();

    public GameEntry startGame(GameInput gameInput, String username) {
        // username is null exception

        // 8d862454-f96a-4490-a0bf-5000f5d37c41 - Mateusz
        // 2b26018a-7b3a-4f8f-bc7b-be09c21c6a4b - Romek

        if (pendingGamePerUserMap.get(username) != null) {
            throw new IllegalStateException("Game already started for user");
        }

        val people = new ArrayList<>(AVAILABLE_PEOPLE);
        Collections.shuffle(people);
        val list = people.stream().limit(gameInput.size()).toList();
        val pendingGame = new PendingGame(UUID.randomUUID(), username, list);
        pendingGamePerUserMap.put(username, pendingGame);

        return new GameEntry(pendingGame.id(), "Podaj imię osoby nr " + pendingGame.nextPerson().id());
    }

    public GameEntry submitAnswer(AnswerInput answerInput, String username) {
        // username is null exception

        val pendingGame = pendingGamePerUserMap.get(username);

        if (pendingGame == null || !pendingGame.id().equals(answerInput.id())) {
            throw new IllegalStateException("Game not started or invalid id");
        }

        val isOngoing = pendingGame.submitAnswer(answerInput.answer());
        if (isOngoing) {
            return new GameEntry(pendingGame.id(), "Podaj imię osoby nr " + pendingGame.nextPerson().id());
        } else {
            val result = new GameEntry(pendingGame.id(), pendingGame.persons(), pendingGame.answers(), pendingGame.getScore());
            pendingGamePerUserMap.remove(username);
            return result;
        }
    }

    @SneakyThrows
    @Cacheable("ints")
    public Integer getInt(int id) {
        Thread.sleep(2000);
        return new Random().nextInt(700);
    }

}
