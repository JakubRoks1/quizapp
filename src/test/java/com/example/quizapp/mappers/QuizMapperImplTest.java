package com.example.quizapp.mappers;

import com.example.quizapp.fixtures.QuizFixtures;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class QuizMapperImplTest {

    @Test
    void givenQuizEntity_whenMapToQuiz_thenCorrect() {
        var givenEntity = QuizFixtures.getQuizEntity();


        QuizMapper mapper = new QuizMapperImpl();
        val result = mapper.mapToQuiz(givenEntity);
        System.out.println(result);


        Assertions.assertThat(result)
            .isNotNull()
            .satisfies(quiz -> {
                Assertions.assertThat(quiz.getId()).isEqualTo(QuizFixtures.getQuiz().getId());
                Assertions.assertThat(quiz.getQuizCategory()).isEqualTo(QuizFixtures.getQuiz().getQuizCategory());
                Assertions.assertThat(quiz.getDescription()).isEqualTo(QuizFixtures.getQuiz().getDescription());
                Assertions.assertThat(quiz.getQuestions()).hasSameSizeAs(QuizFixtures.getQuiz().getQuestions());
            });
    }
}