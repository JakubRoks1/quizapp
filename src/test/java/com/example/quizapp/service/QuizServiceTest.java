package com.example.quizapp.service;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.entity.QuizEntity;
import com.example.quizapp.exception.ExceptionType;
import com.example.quizapp.exception.QuizAppException;
import com.example.quizapp.mappers.QuizMapper;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("service")
class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    private QuizMapper quizMapper;

    @InjectMocks
    private QuizService quizService;

    private Quiz quiz;
    private QuizEntity quizEntity;
    private Long quizId;

    @BeforeEach
    void setUp() {
        quizMapper = Mappers.getMapper(QuizMapper.class);
        quizService = new QuizService(quizRepository, quizMapper);
        quizId = 1L;

        quiz = new Quiz();
        quiz.setId(quizId);
        quiz.setQuizCategory("Test Category");
        quiz.setDescription("Test Description");
        quizEntity = quizMapper.mapToQuizEntity(quiz);
    }

    @Test
    void givenQuizWithQuestions_whenGetQuiz_thenShouldReturnQuizWithQuestions() {
        Question question = new Question();
        question.setId(1L);
        quiz.setQuestions(Set.of(question));

        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(1L);
        quizEntity.setQuestions(Set.of(questionEntity));

        when(quizRepository.findByIdWithQuestions(quizId)).thenReturn(Optional.of(quizEntity));

        Optional<Quiz> result = quizService.getQuiz(quizId, true);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(quizEntity.getId());
        assertThat(result.get().getQuestions()).hasSize(1);

        verify(quizRepository).findByIdWithQuestions(quizId);
    }

    @Test
    void givenQuizWithoutQuestions_whenGetQuiz_thenShouldReturnQuizWithoutQuestions() {
        QuizEntity entityWithoutQuestions = quizMapper.mapToQuizEntity(quiz);
        entityWithoutQuestions.setQuestions(Set.of());

        when(quizRepository.findByIdWithoutQuestions(quizId)).thenReturn(Optional.of(entityWithoutQuestions));

        Optional<Quiz> result = quizService.getQuiz(quizId, false);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(entityWithoutQuestions.getId());
        assertThat(result.get().getQuestions()).isEmpty();

        verify(quizRepository).findByIdWithoutQuestions(quizId);
    }

    @Test
    void givenValidQuiz_whenAddQuiz_thenShouldSaveAndReturnQuiz() {
        when(quizRepository.save(any(QuizEntity.class))).thenReturn(quizEntity);

        Quiz result = quizService.addQuiz(quiz);

        assertThat(result).isNotNull();
        assertThat(result.getQuizCategory()).isEqualTo("Test Category");
        assertThat(result.getDescription()).isEqualTo("Test Description");

        verify(quizRepository).save(any(QuizEntity.class));
    }

    @Test
    void givenQuizWithNullValues_whenAddQuiz_thenShouldSaveWithNullValues() {
        Quiz emptyQuiz = new Quiz();
        QuizEntity emptyEntity = quizMapper.mapToQuizEntity(emptyQuiz);
        emptyEntity.setId(quizId);

        when(quizRepository.save(any(QuizEntity.class))).thenReturn(emptyEntity);

        Quiz result = quizService.addQuiz(emptyQuiz);

        assertThat(result).isNotNull();
        assertThat(result.getQuizCategory()).isNull();
        assertThat(result.getDescription()).isNull();

        verify(quizRepository).save(any(QuizEntity.class));
    }

    @Test
    void givenRepositoryThrowsException_whenAddQuiz_thenShouldPropagateException() {
        when(quizRepository.save(any(QuizEntity.class))).thenThrow(new RuntimeException("Database save error"));

        assertThatThrownBy(() -> quizService.addQuiz(quiz))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Database save error");

        verify(quizRepository).save(any(QuizEntity.class));
    }

    @Test
    void givenExistingQuiz_whenUpdateQuiz_thenShouldUpdateSuccessfully() {
        Quiz updateRequest = new Quiz();
        updateRequest.setQuizCategory("New Category");
        updateRequest.setDescription("New Description");

        QuizEntity existingEntity = quizMapper.mapToQuizEntity(quiz);
        QuizEntity updatedEntity = quizMapper.mapToQuizEntity(updateRequest);
        updatedEntity.setId(quizId);

        when(quizRepository.findById(quizId)).thenReturn(Optional.of(existingEntity));
        when(quizRepository.save(any(QuizEntity.class))).thenReturn(updatedEntity);

        Quiz result = quizService.updateQuiz(quizId, updateRequest);

        assertThat(result).isNotNull();
        assertThat(result.getQuizCategory()).isEqualTo("New Category");
        assertThat(result.getDescription()).isEqualTo("New Description");

        verify(quizRepository).findById(quizId);
        verify(quizRepository).save(any(QuizEntity.class));
    }

    @Test
    void givenNonExistingQuiz_whenUpdateQuiz_thenShouldThrowException() {
        Quiz updateRequest = new Quiz();
        when(quizRepository.findById(quizId)).thenReturn(Optional.empty());

        QuizAppException exception = assertThrows(QuizAppException.class,
                () -> quizService.updateQuiz(quizId, updateRequest));

        assertThat(exception.getMessage()).isEqualTo(ExceptionType.QUIZ_NOT_FOUND.getMessage());
        assertThat(exception.getExceptionType()).isEqualTo(ExceptionType.QUIZ_NOT_FOUND);

        verify(quizRepository).findById(quizId);
        verify(quizRepository, never()).save(any(QuizEntity.class));
    }

    @Test
    void givenEmptyQuizList_whenGetAllQuizzes_thenShouldReturnEmptyList() {
        when(quizRepository.findAll()).thenReturn(Collections.emptyList());

        List<Quiz> result = quizService.getAllQuizzes();

        assertThat(result).isEmpty();

        verify(quizRepository).findAll();
    }

    @Test
    void givenMultipleQuizzes_whenGetAllQuizzes_thenShouldReturnAllQuizzes() {
        List<QuizEntity> entities = Arrays.asList(quizEntity, quizEntity);
        when(quizRepository.findAll()).thenReturn(entities);

        List<Quiz> result = quizService.getAllQuizzes();

        assertThat(result).hasSize(2);

        verify(quizRepository).findAll();
    }

    @Test
    void givenQuizWithoutQuestions_whenDeleteQuiz_thenShouldDeleteSuccessfully() {
        QuizEntity emptyQuizEntity = quizMapper.mapToQuizEntity(quiz);
        emptyQuizEntity.setId(quizId);
        emptyQuizEntity.setQuestions(Collections.emptySet());

        when(quizRepository.findByIdWithQuestions(quizId)).thenReturn(Optional.of(emptyQuizEntity));

        boolean result = quizService.deleteQuiz(quizId);

        assertThat(result).isTrue();

        verify(quizRepository).findByIdWithQuestions(quizId);
        verify(quizRepository).delete(emptyQuizEntity);
    }

    @Test
    void givenQuizWithQuestions_whenDeleteQuiz_thenShouldThrowException() {
        QuizEntity quizWithQuestions = quizMapper.mapToQuizEntity(quiz);
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(1L);
        quizWithQuestions.setQuestions(Set.of(questionEntity));

        when(quizRepository.findByIdWithQuestions(quizId)).thenReturn(Optional.of(quizWithQuestions));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> quizService.deleteQuiz(quizId));

        assertThat(exception.getMessage()).isEqualTo("Cannot delete quiz with questions");

        verify(quizRepository, never()).delete(any(QuizEntity.class));
    }

    @Test
    void givenNonExistingQuiz_whenDeleteQuiz_thenShouldReturnFalse() {
        when(quizRepository.findByIdWithQuestions(quizId)).thenReturn(Optional.empty());

        boolean result = quizService.deleteQuiz(quizId);

        assertThat(result).isFalse();

        verify(quizRepository, never()).delete(any(QuizEntity.class));
    }
}