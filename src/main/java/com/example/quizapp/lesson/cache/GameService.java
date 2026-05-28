package com.example.quizapp.lesson.cache;

import lombok.SneakyThrows;
import lombok.val;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

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
    @Caching(
        cacheable = {
            @Cacheable(value = "ints", condition = "#id < 100"),
            @Cacheable(value = "ints-wszystkie")
        }
    )
    public Integer getInt(int id) {
        Thread.sleep(2000); // pobranie z bazy danych
        int ret = new Random().nextInt(700);

        return ret;
    }

    @Cacheable(value = "ints", unless = "#result == null")
    public Integer getIntOnly(int id) {
        return null;
    }

    @CachePut("ints")
    public Integer putInt(int id) {
        return -id;
    }

    @CacheEvict("ints")
    public void evictInt(int id) {
    }

    @CacheEvict(value = "ints", allEntries = true)
    public void evictIntAll() {
    }
}


/**
 *
 *  Mateusz - dzis o 20
 *  Quiz Astronomia
 *  4) Jaka jest 6 planeta?
 *  - Mars
 *  - Jowisz
 *  17) Ile jest gwiazd na niebie?
 *  - 100
 *  - 4 500 000
 *  Koniec gry
 *  zdobyto punktów 0
 *
 *
 *  QUIZY
 *  1 Astronomia
 *  2 Geografia
 *
 * PYTANIA
 * ID_QUIZU, ID_PYTANIA, PYTANIE, ODPOWIEDZI
 * 1, 1, Którą planeta jest Ziemia, 3
 * ...
 * 1, 4, Jaka jest szósta planeta?, Jowisz
 *
 *
 * GAME_HISTORY
 * Jaka jest szósta planeta?, Jowisz , Mars
 *
 */
