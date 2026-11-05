package com.example.quizapp.lesson.internationalization;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MyAppException extends RuntimeException {

    @Getter
    private final Code code;

    public MyAppException(Code code) {
        this.code = code;
    }

    @AllArgsConstructor
    public enum Code {
        NOT_USER_FOUND("exception.no-user-found"),
        INTERNAL_ERROR("exception.internal-error");

        @Getter
        private final String key;
    }
}
