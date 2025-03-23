package com.example.quizapp.exception;

import lombok.Getter;

@Getter
public enum ExceptionType {
    QUIZ_NOT_FOUND("ER-001", "Quiz not found"),
    QUESTION_NOT_FOUND("ER-002", "Question not found"),
    ANSWER_NOT_FOUND("ER-003", "Answer not found"),
    VALIDATION_EXCEPTION("ER-004", "Validation exception"),
    INVALID_FETCH_MODE("ER-005", "Invalid fetch mode provided"),
    UNSUPPORTED_TARGET_TYPE("ER-006", "Unsupported target type");

    private final String message;
    private final String errorCode;

    ExceptionType(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
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
