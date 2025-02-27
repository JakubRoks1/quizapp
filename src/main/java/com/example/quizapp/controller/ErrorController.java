package com.example.quizapp.controller;

import com.example.quizapp.exception.ExceptionType;
import com.example.quizapp.json.QuizJson;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/er")
public class ErrorController {

    @GetMapping("/1")
    public void a() {
        throw ExceptionType.QUESTION_NOT_FOUND.getException();
    }

    @GetMapping("/2")
    public void b() {
        throw ExceptionType.VALIDATION_EXCEPTION.getException();
    }

    @GetMapping("/3")
    public void c() throws IOException {
        throw new IOException("ioekspepsion");
    }

    @GetMapping("/4")
    public void d() throws IOException {
        throw new ResponseStatusException(101, "ioekspepsion", null);
    }

    @SneakyThrows
    public void other() {
//        try {
//            throwEx();
//        } catch (MyException e) {
//            throw new RuntimeException(e);
//        }
        throwEx();
    }


    public void throwEx() throws MyException {
        throw new MyException("not found entry");
    }

    public void throwEx2() {
        throw new ValidationException("Invalid input - validation error");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class MyException extends Exception {
        public MyException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }

    /////////////


//    @ExceptionHandler(value = {MyException.class, ValidationException.class})
    public ResponseEntity<QuizJson> handleMyException(Exception e) {
        System.out.println(e.getMessage());
        var quizJson = new QuizJson();
        quizJson.setId(-10000000L);
        quizJson.setQuizCategory(e.getMessage());
        quizJson.setDescription(e.getClass().getName());

        if (e instanceof MyException) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(quizJson);
        } else if (e instanceof ValidationException) {
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(quizJson);
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(quizJson);
        }
    }
}
