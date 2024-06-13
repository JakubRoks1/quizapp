package com.example.quizapp.controller;

import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping
    @ResponseStatus(value = HttpStatus.IM_USED)
    public Integer pm() {
        return 1;
    }

    @PostMapping(path = "/a")
    @ResponseStatus(value = HttpStatus.BAD_GATEWAY)
    public Integer pm2() {
        return 2;
    }

    @GetMapping
    public ResponseEntity<Integer> gm() {
//        var responseEntity = new ResponseEntity<>(3, HttpStatus.ACCEPTED);

        val re = ResponseEntity.status(900).header("app", "moja-aplikacje").body(4);

        return re;
    }

    @GetMapping("/{resultCode}/{number}")
    public ResponseEntity<Integer> gm2(@PathVariable Integer resultCode, @PathVariable(value = "number", required = false) Integer numerek) {
        return ResponseEntity.status(resultCode).body(numerek);
    }
}
