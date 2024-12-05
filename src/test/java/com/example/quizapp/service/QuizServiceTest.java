package com.example.quizapp.service;

import com.example.quizapp.entity.QuizEntity;
import com.example.quizapp.fixtures.QuizFixtures;
import com.example.quizapp.mappers.QuizMapperImpl;
import com.example.quizapp.repository.QuizRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {


    @Test
    void givenQuizEntity_whenGetQuizWithQuestions_thenReturnQuizWithQuestions() {
        ///
        QuizEntity givenEntity = QuizFixtures.getQuizEntity();

//        var quiz1 = new Quiz();
//        quiz1.setId(300L);
//        quiz1.setQuizCategory("Kosmos");


        var mockRepo = Mockito.mock(QuizRepository.class);
        BDDMockito.given(mockRepo.findByIdWithQuestions(1L)).willReturn(Optional.of(givenEntity));
//        BDDMockito.given(mockRepo.findByIdWithQuestions(2L)).willReturn(Optional.of(givenEntity));

//        var mockMapper = Mockito.mock(QuizMapper.class);
//        BDDMockito.given(mockMapper.mapToQuiz(givenEntity)).willReturn(quiz1);

        var mockMapper = new QuizMapperImpl();

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        var quiz = quizService.getQuiz(1L, true);
        System.out.println(quiz);

        Assertions.assertThat(quiz).isNotEmpty();
        Assertions.assertThat(quiz.get().getId()).isEqualTo(givenEntity.getId());
        Assertions.assertThat(quiz.get().getQuestions()).hasSameSizeAs(givenEntity.getQuestions());
    }

    @Test
    void givenQuizEntity_whenGetQuizWithoutQuestions_thenReturnQuizWithoutQuestions() {
        ///
        QuizEntity givenEntity = QuizFixtures.getQuizEntity();
        givenEntity.setQuestions(Set.of());

//        var quiz1 = new Quiz();
//        quiz1.setId(300L);
//        quiz1.setQuizCategory("Kosmos");


        var mockRepo = Mockito.mock(QuizRepository.class);
        BDDMockito.given(mockRepo.findByIdWithoutQuestions(1L)).willReturn(Optional.of(givenEntity));
//        BDDMockito.given(mockRepo.findByIdWithQuestions(2L)).willReturn(Optional.of(givenEntity));

//        var mockMapper = Mockito.mock(QuizMapper.class);
//        BDDMockito.given(mockMapper.mapToQuiz(givenEntity)).willReturn(quiz1);

        var mockMapper = new QuizMapperImpl();

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        var quiz = quizService.getQuiz(1L, false);
        System.out.println(quiz);

        Assertions.assertThat(quiz).isNotEmpty();
        Assertions.assertThat(quiz.get().getId()).isEqualTo(givenEntity.getId());
        Assertions.assertThat(quiz.get().getQuestions()).isEmpty();
    }
}