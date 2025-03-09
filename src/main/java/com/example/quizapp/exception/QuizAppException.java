package com.example.quizapp.exception;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Praca domowa
 * xPozbyć się w aplikacji wszystkich innych exceptionów - używamy tylko ExceptionType, usuń wszystkie try-catch
 * Poprawić testy - stare
 * xNapisać testy nowe - czyli przetestować QuizAppException (sprawdzić timestamp - ew. przygotować kilka rozwiązań), i ExceptionType (@ParametrizedTest / @EnumSource)
 * Napisać test Controllera gdzie leci błąd (MockMVC)
 * xRozserzyć QuizAppException o kod-błędu (ER-001, ER-002) dodać to do body
 * x  - ** dodać pole do exceptiona (i finalnie do body), nazwa klasy gdzie poleciał błąd - bez podawania w parametrze (java-reflect)
 */
@Getter
public class QuizAppException extends RuntimeException {
    private final ExceptionType exceptionType;
    private final boolean withBody;
    private final LocalDateTime timestamp;
    private final String errorCode;
    private final String className;

    public QuizAppException(ExceptionType exceptionType, boolean withBody) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
        this.withBody = withBody;
        this.timestamp = LocalDateTime.now();
        this.errorCode = generateErrorCode();
        this.className = getCallerClassName();
    }

    private String generateErrorCode() {
        return "ER-" + String.format("%03d", exceptionType.ordinal() + 1);
    }

    private String getCallerClassName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            if (!element.getClassName().equals(this.getClass().getName()) &&
                    element.getClassName().startsWith("com.example.quizapp")) {
                return element.getClassName();
            }
        }
        return "Unknown";
    }
}
