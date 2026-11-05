package com.example.quizapp.lesson.internationalization;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@Component
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandlerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler(MyAppException.class)
    ProblemDetail handle(MyAppException ex, Locale locale) {
        var msg = messageSource.getMessage(ex.getCode().getKey(), null, locale);
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.I_AM_A_TEAPOT, msg);
        problemDetail.setProperty("my-code", ex.getCode());
        return problemDetail;
    }
}
