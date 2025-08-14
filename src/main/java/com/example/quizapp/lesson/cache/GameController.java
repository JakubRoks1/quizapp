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

    @GetMapping("/cacheOnly")
    public ResponseEntity<Integer> cacheOnly(@RequestParam Integer id) {
        System.out.println("start " + LocalDateTime.now());
        var i = gameService.getIntOnly(id);
        System.out.println("end " + LocalDateTime.now());
        return ResponseEntity.ok(i);
    }

    @GetMapping("/cachePut")
    public ResponseEntity<String> cachePut(@RequestParam Integer id) {
        gameService.putInt(id);
        return ResponseEntity.ok("Dodano");
    }

    @GetMapping("/cacheEvict")
    public ResponseEntity<String> cacheEvict(@RequestParam Integer id) {
        gameService.evictInt(id);
        return ResponseEntity.ok("Usunieto");
    }

    @GetMapping("/cacheEvictAll")
    public ResponseEntity<String> cacheEvict2() {
        gameService.evictIntAll();
        return ResponseEntity.ok("Usunieto");
    }
}
