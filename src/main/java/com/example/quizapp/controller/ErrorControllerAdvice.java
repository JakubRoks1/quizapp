package com.example.quizapp.controller;

import com.example.quizapp.json.QuizJson;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 1) Własna klasa exceptiona - QuizAppException / RuntimeException
 *    - int code
 *    - String message
 *    - LocalDateTime timestamp
 * 2) zamienić wszystkie exceptiony w aplikacji
 * 3) Globalny ErrorHandler to globalny model odpowiedzi ErrorJson - mapowanie może odbywać się przez mapstructa
 */
@ControllerAdvice(assignableTypes = {ErrorController.class, AnswerController.class})
public class ErrorControllerAdvice {

    @ExceptionHandler(value = {ErrorController.MyException.class, ErrorController.ValidationException.class})
    public ResponseEntity<QuizJson> handleMyException(Exception e) {
        System.out.println(e.getMessage());
        var quizJson = new QuizJson();
        quizJson.setId(-9L);
        quizJson.setQuizCategory(e.getMessage());
        quizJson.setDescription(e.getClass().getName());

        if (e instanceof ErrorController.MyException) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(quizJson);
        } else if (e instanceof ErrorController.ValidationException) {
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(quizJson);
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(quizJson);
        }
    }
}
