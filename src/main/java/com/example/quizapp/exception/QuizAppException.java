package com.example.quizapp.exception;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Praca domowa
 * Pozbyć się w aplikacji wszystkich innych exceptionów - używamy tylko ExceptionType, usuń wszystkie try-catch
 * Poprawić testy - stare
 * Napisać testy nowe - czyli przetestować QuizAppException (sprawdzić timestamp - ew. przygotować kilka rozwiązań), i ExceptionType (@ParametrizedTest / @EnumSource)
 * Napisać test Controllera gdzie leci błąd (MockMVC)
 * Rozserzyć QuizAppException o kod-błędu (ER-001, ER-002) dodać to do body
 *   - ** dodać pole do exceptiona (i finalnie do body), nazwa klasy gdzie poleciał błąd - bez podawania w parametrze (java-reflect)
 */
@Getter
public class QuizAppException extends RuntimeException {
    private final ExceptionType exceptionType;
    private final boolean withBody;
    private final LocalDateTime timestamp;

    public QuizAppException(ExceptionType exceptionType, boolean withBody) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
        this.withBody = withBody;
        this.timestamp = LocalDateTime.now();
    }
}
