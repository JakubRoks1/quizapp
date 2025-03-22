package com.example.quizapp.json;

import com.example.quizapp.exception.ExceptionType;


public enum FetchMode {
    FULL,
    SHORT,
    COUNT;

    public static FetchMode fromString(String mode) {
        if (mode == null || mode.isEmpty()) {
            throw ExceptionType.INVALID_FETCH_MODE.getExceptionWithBody();
        }

        for (FetchMode fetchMode : FetchMode.values()) {
            if (fetchMode.name().equalsIgnoreCase(mode)) {
                return fetchMode;
            }
        }

        throw ExceptionType.INVALID_FETCH_MODE.getExceptionWithBody();
    }

    public static Class<?> getJsonViewBasedOnFetchMode(FetchMode mode, Class<?> targetType) {
        if (targetType == QuizJson.class) {
            return switch (mode) {
                case FULL -> QuizJson.Views.GetFull.class;
                case SHORT -> QuizJson.Views.GetShort.class;
                case COUNT -> QuizJson.Views.GetWithCount.class;
            };
        } else if (targetType == QuestionJson.class) {
            return switch (mode) {
                case FULL -> QuestionJson.Views.GetFull.class;
                case SHORT -> QuestionJson.Views.GetShort.class;
                case COUNT -> QuestionJson.Views.GetWithCount.class;
            };
        } else {
            throw ExceptionType.UNSUPPORTED_TARGET_TYPE.getExceptionWithBody();
        }
    }
}
