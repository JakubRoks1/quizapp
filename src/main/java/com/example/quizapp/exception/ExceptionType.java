package com.example.quizapp.exception;

import lombok.Getter;

@Getter
public enum ExceptionType {
    QUIZ_NOT_FOUND("ER-001", "Quiz not found", 404),
    QUESTION_NOT_FOUND("ER-002", "Question not found", 404),
    ANSWER_NOT_FOUND("ER-003", "Answer not found", 404),
    VALIDATION_EXCEPTION("ER-004", "Validation exception", 400),
    INVALID_FETCH_MODE("ER-005", "Invalid fetch mode provided", 400),
    UNSUPPORTED_TARGET_TYPE("ER-006", "Unsupported target type", 400);

    private final String errorCode;
    private final String message;
    private final Integer code;

    ExceptionType(String errorCode, String message, Integer code) {
        this.message = message;
        this.errorCode = errorCode;
        this.code = code;
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
