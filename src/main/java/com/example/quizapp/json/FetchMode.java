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
            throw new IllegalArgumentException("Unsupported target type");
        }
    }
}
