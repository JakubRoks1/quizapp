package com.example.quizapp.exception;

import lombok.Getter;

@Getter
public enum ExceptionType {
    QUIZ_NOT_FOUND(404, "Quiz not found"),
    QUESTION_NOT_FOUND(404, "Question not found"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    VALIDATION_EXCEPTION(400, "Validation exception");

    private final Integer code;
    private final String message;

    ExceptionType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public QuizAppException getException() {
        return new QuizAppException(this, false);
    }

    public QuizAppException getExceptionWithBody() {
        return new QuizAppException(this, true);
    }

    public QuizAppException getException(boolean withBody) {
        return new QuizAppException(this, withBody);
    }
}
