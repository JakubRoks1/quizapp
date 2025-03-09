package com.example.quizapp.exception;

public class TestQuizAppException {

    public static void main(String[] args) {
        try {
            throw new QuizAppException(ExceptionType.QUIZ_NOT_FOUND, true);
        } catch (QuizAppException e) {
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Class Name: " + e.getClassName());
            System.out.println("Exception Message: " + e.getMessage());
            System.out.println("Timestamp: " + e.getTimestamp());
        }
    }
}

