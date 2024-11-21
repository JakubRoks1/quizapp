package com.example.quizapp.json;

public enum FetchMode {
    FULL,
    SHORT,
    COUNT;

    public static FetchMode fromString(String mode) {
        try {
            return FetchMode.valueOf(mode.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static Class<?> getQuizJsonViewBasedOnFetchMode(FetchMode mode) {
        return switch(mode) {
            case FULL -> QuizJson.Views.GetFull.class;
            case SHORT -> QuizJson.Views.GetShort.class;
            case COUNT -> QuizJson.Views.GetWithCount.class;
        };
    }
}
