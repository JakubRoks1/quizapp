package com.example.quizapp.json;

public enum FetchMode {
    FULL,
    SHORT;

    public static FetchMode fromString(String mode) {
        try {
            return FetchMode.valueOf(mode.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
