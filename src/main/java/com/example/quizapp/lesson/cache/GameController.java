package com.example.quizapp.lesson.cache;

import lombok.val;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.example.quizapp.lesson.cache.Constants.HEADER_USERNAME;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final CacheManager cacheManager;

    public GameController(GameService gameService, CacheManager cacheManager) {
        this.gameService = gameService;
        this.cacheManager = cacheManager;
    }

    @PostMapping("/start")
    public ResponseEntity<?> startGame(@RequestBody GameInput gameInput, @RequestHeader(HEADER_USERNAME) String username) {
        val people = gameService.startGame(gameInput, username);

        return ResponseEntity.ok(people);
    }

    @PostMapping("/answer")
    public ResponseEntity<?> answer(@RequestBody AnswerInput answerInput, @RequestHeader(HEADER_USERNAME) String username) {
        return ResponseEntity.ok(gameService.submitAnswer(answerInput, username));
    }

    @GetMapping("/cache")
    public ResponseEntity<Integer> cache(@RequestParam Integer id) {
        System.out.println("start " + LocalDateTime.now());
        var i = gameService.getInt(id);
        System.out.println("end " + LocalDateTime.now());

        return ResponseEntity.ok(i);
    }
}
