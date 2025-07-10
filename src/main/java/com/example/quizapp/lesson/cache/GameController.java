package com.example.quizapp.lesson.cache;

import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.quizapp.lesson.cache.Constants.HEADER_USERNAME;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
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
}
