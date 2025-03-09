package com.example.quizapp.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.*;

class QuizAppExceptionTest {

    @Test
    void constructor_shouldSetAllFields() {
        ExceptionType exceptionType = ExceptionType.QUIZ_NOT_FOUND;
        boolean withBody = true;

        QuizAppException exception = new QuizAppException(exceptionType, withBody);

        assertThat(exception)
                .extracting(
                        QuizAppException::getExceptionType,
                        QuizAppException::isWithBody,
                        QuizAppException::getErrorCode,
                        QuizAppException::getClassName
                )
                .containsExactly(
                        exceptionType,
                        withBody,
                        "ER-001",
                        this.getClass().getName()
                );

        assertThat(exception.getTimestamp()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
    }


    @ParameterizedTest
    @EnumSource(ExceptionType.class)
    void exceptionType_shouldHaveCorrectErrorCode(ExceptionType exceptionType) {
        String expectedErrorCode = "ER-" + String.format("%03d", exceptionType.ordinal() + 1);

        QuizAppException exception = new QuizAppException(exceptionType, true);

        assertThat(exception.getErrorCode()).isEqualTo(expectedErrorCode);
    }

    @ParameterizedTest
    @EnumSource(ExceptionType.class)
    void exceptionType_shouldHaveCorrectMessage(ExceptionType exceptionType) {
        QuizAppException exception = new QuizAppException(exceptionType, true);

        assertThat(exception)
                .hasMessage(exceptionType.getMessage())
                .extracting(QuizAppException::getMessage)
                .isEqualTo(exceptionType.getMessage());
    }

    @Test
    void timestamp_shouldBeCloseToCurrentTime() {
        LocalDateTime before = LocalDateTime.now();

        QuizAppException exception = new QuizAppException(ExceptionType.QUIZ_NOT_FOUND, true);
        LocalDateTime after = LocalDateTime.now();

        assertThat(exception.getTimestamp())
                .isBetween(before, after)
                .isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
    }

    @Test
    void timestamp_shouldBeConsistentAcrossMultipleInstantiations() {
        int numberOfInstantiations = 1000;
        LocalDateTime[] timestamps = new LocalDateTime[numberOfInstantiations];

        for (int i = 0; i < numberOfInstantiations; i++) {
            QuizAppException exception = new QuizAppException(ExceptionType.QUIZ_NOT_FOUND, true);
            timestamps[i] = exception.getTimestamp();
        }

        assertThat(timestamps)
                .doesNotContainNull()
                .isSortedAccordingTo(LocalDateTime::compareTo)
                .allSatisfy(timestamp ->
                        assertThat(timestamp).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS))
                );
    }

    @Test
    void className_shouldBeSetCorrectly() {
        QuizAppException exception = new QuizAppException(ExceptionType.QUIZ_NOT_FOUND, true);

        assertThat(exception.getClassName())
                .isEqualTo(this.getClass().getName());
    }


}
