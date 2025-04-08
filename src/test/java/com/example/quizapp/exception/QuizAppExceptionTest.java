package com.example.quizapp.exception;

import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class QuizAppExceptionTest {

    @ParameterizedTest
    @EnumSource(ExceptionType.class)
    void givenExceptionType_whenConstructorInvoked_thenShouldSetAllFields(ExceptionType givenExceptionType) {
        boolean withBody = true;
        val givenLocalDateTime = LocalDateTime.of(2025, 1, 1, 12, 0, 0);

        QuizAppException exception;
        try (MockedStatic<LocalDateTime> mocked = Mockito.mockStatic(LocalDateTime.class)) {
            mocked.when(LocalDateTime::now).thenReturn(givenLocalDateTime);
            exception = new QuizAppException(givenExceptionType, withBody);
        }

        String expectedErrorCode = "ER-" + String.format("%03d", givenExceptionType.ordinal() + 1);

        assertThat(exception)
            .satisfies(ex -> {
                assertThat(ex.getExceptionType()).isEqualTo(givenExceptionType);
                assertThat(ex.isWithBody()).isEqualTo(withBody);
                assertThat(ex.getErrorCode()).isEqualTo(expectedErrorCode);
                assertThat(ex.getClassName()).isEqualTo(QuizAppExceptionTest.class.getName());
                assertThat(ex.getTimestamp()).isEqualTo(givenLocalDateTime);
            });

        System.out.println(exception.getTimestamp());
    }
}
