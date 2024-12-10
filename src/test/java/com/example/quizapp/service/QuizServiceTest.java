package com.example.quizapp.service;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.entity.QuizEntity;
import com.example.quizapp.fixtures.QuizFixtures;
import com.example.quizapp.mappers.QuizMapper;
import com.example.quizapp.mappers.QuizMapperImpl;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.repository.QuizRepository;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

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

    @Test
    void givenValidQuiz_whenAddQuiz_thenQuizSavedSuccessfully() {
        Quiz newQuiz = new Quiz();
        newQuiz.setQuizCategory("Science");
        newQuiz.setDescription("Physics Quiz");

        QuizEntity quizEntity = new QuizEntity();
        quizEntity.setId(1L);
        quizEntity.setQuizCategory("Science");
        quizEntity.setDescription("Physics Quiz");

        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        BDDMockito.given(mockMapper.mapToQuizEntity(newQuiz))
                .willReturn(quizEntity);
        BDDMockito.given(mockRepo.save(quizEntity))
                .willReturn(quizEntity);
        BDDMockito.given(mockMapper.mapToQuiz(quizEntity))
                .willReturn(newQuiz);

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        Quiz savedQuiz = quizService.addQuiz(newQuiz);


        assertNotNull(savedQuiz);
        assertEquals("Science", savedQuiz.getQuizCategory());
        assertEquals("Physics Quiz", savedQuiz.getDescription());

        Mockito.verify(mockMapper).mapToQuizEntity(newQuiz);
        Mockito.verify(mockRepo).save(quizEntity);
        Mockito.verify(mockMapper).mapToQuiz(quizEntity);
        System.out.println(newQuiz);
    }

    @Test
    void givenQuizWithNullValues_whenAddQuiz_thenQuizSavedWithNullValues() {
        Quiz newQuiz = new Quiz();

        QuizEntity quizEntity = new QuizEntity();
        quizEntity.setId(1L);

        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        BDDMockito.given(mockMapper.mapToQuizEntity(newQuiz))
                .willReturn(quizEntity);
        BDDMockito.given(mockRepo.save(quizEntity))
                .willReturn(quizEntity);
        BDDMockito.given(mockMapper.mapToQuiz(quizEntity))
                .willReturn(newQuiz);

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        Quiz savedQuiz = quizService.addQuiz(newQuiz);

        assertNotNull(savedQuiz);
        assertNull(savedQuiz.getQuizCategory());
        assertNull(savedQuiz.getDescription());
        System.out.println(newQuiz);
    }

    @Test
    void givenRepositorySaveThrowsException_whenAddQuiz_thenExceptionThrown() {
        Quiz newQuiz = new Quiz();
        newQuiz.setQuizCategory("Math");

        QuizEntity quizEntity = new QuizEntity();

        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        BDDMockito.given(mockMapper.mapToQuizEntity(newQuiz))
                .willReturn(quizEntity);

        BDDMockito.given(mockRepo.save(quizEntity))
                .willThrow(new RuntimeException("Database save error"));

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        assertThrows(RuntimeException.class, () -> {
            quizService.addQuiz(newQuiz);
        });
        System.out.println(newQuiz);
    }

    @Test
    void givenExistingQuiz_whenUpdateQuiz_thenQuizUpdatedSuccessfully() {
        Long quizId = 1L;

        QuizEntity existingQuizEntity = new QuizEntity();
        existingQuizEntity.setId(quizId);
        existingQuizEntity.setQuizCategory("Old Category");
        existingQuizEntity.setDescription("Old Description");

        Quiz quizUpdateRequest = new Quiz();
        quizUpdateRequest.setQuizCategory("New Category");
        quizUpdateRequest.setDescription("New Description");

        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        BDDMockito.given(mockRepo.findById(quizId))
                .willReturn(Optional.of(existingQuizEntity));

        QuizEntity updatedEntity = new QuizEntity();
        updatedEntity.setId(quizId);
        updatedEntity.setQuizCategory("New Category");
        updatedEntity.setDescription("New Description");

        BDDMockito.given(mockRepo.save(existingQuizEntity))
                .willReturn(updatedEntity);

        BDDMockito.given(mockMapper.mapToQuiz(updatedEntity))
                .willReturn(quizUpdateRequest);

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        Quiz updatedQuiz = quizService.updateQuiz(quizId, quizUpdateRequest);

        assertNotNull(updatedQuiz);
        assertEquals("New Category", updatedQuiz.getQuizCategory());
        assertEquals("New Description", updatedQuiz.getDescription());

        Mockito.verify(mockRepo).findById(quizId);
        Mockito.verify(mockMapper).updateQuizFromDto(quizUpdateRequest, existingQuizEntity);
        Mockito.verify(mockRepo).save(existingQuizEntity);
    }

    @Test
    void givenNonExistingQuiz_whenUpdateQuiz_thenThrowException() {
        Long quizId = 1L;

        Quiz quizUpdateRequest = new Quiz();
        quizUpdateRequest.setQuizCategory("New Category");

        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        BDDMockito.given(mockRepo.findById(quizId))
                .willReturn(Optional.empty());

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            quizService.updateQuiz(quizId, quizUpdateRequest);
        });

        assertEquals("nie-ma", exception.getMessage());

        Mockito.verify(mockRepo).findById(quizId);
        Mockito.verify(mockMapper, Mockito.never()).updateQuizFromDto(any(), any());
        Mockito.verify(mockRepo, Mockito.never()).save(any());

    }

    @Test
    void givenExistingQuiz_whenPartialUpdate_thenOnlySpecifiedFieldsUpdated() {
        Long quizId = 1L;

        QuizEntity existingQuizEntity = new QuizEntity();
        existingQuizEntity.setId(quizId);
        existingQuizEntity.setQuizCategory("Old Category");
        existingQuizEntity.setDescription("Old Description");

        Quiz partialUpdateRequest = new Quiz();
        partialUpdateRequest.setQuizCategory("New Category");

        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        BDDMockito.given(mockRepo.findById(quizId))
                .willReturn(Optional.of(existingQuizEntity));

        doAnswer(invocation -> {
            Quiz quiz = invocation.getArgument(0);
            QuizEntity entity = invocation.getArgument(1);

            if (quiz.getQuizCategory() != null) {
                entity.setQuizCategory(quiz.getQuizCategory());
            }

            return null;
        }).when(mockMapper).updateQuizFromDto(partialUpdateRequest, existingQuizEntity);

        QuizEntity updatedEntity = new QuizEntity();
        updatedEntity.setId(quizId);
        updatedEntity.setQuizCategory("New Category");
        updatedEntity.setDescription("Old Description");

        BDDMockito.given(mockRepo.save(existingQuizEntity))
                .willReturn(updatedEntity);

        Quiz mappedQuiz = new Quiz();
        mappedQuiz.setId(quizId);
        mappedQuiz.setQuizCategory("New Category");
        mappedQuiz.setDescription("Old Description");

        BDDMockito.given(mockMapper.mapToQuiz(updatedEntity))
                .willReturn(mappedQuiz);

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        Quiz updatedQuiz = quizService.updateQuiz(quizId, partialUpdateRequest);

        assertNotNull(updatedQuiz);
        assertEquals("New Category", updatedQuiz.getQuizCategory());
        assertEquals("Old Description", updatedQuiz.getDescription());

        Mockito.verify(mockRepo).findById(quizId);
        Mockito.verify(mockMapper).updateQuizFromDto(partialUpdateRequest, existingQuizEntity);
        Mockito.verify(mockRepo).save(existingQuizEntity);

    }

    @Test
    void givenNoQuizzes_whenGetAllQuizzes_thenReturnEmptyList() {
        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        BDDMockito.given(mockRepo.findAll())
                .willReturn(Collections.emptyList());

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        List<Quiz> quizzes = quizService.getAllQuizzes();

        assertTrue(quizzes.isEmpty());
        Mockito.verify(mockRepo).findAll();

        System.out.println(quizzes);
    }

    @Test
    void givenMultipleQuizzes_whenGetAllQuizzes_thenReturnCorrectNumberOfQuizzes() {
        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        List<QuizEntity> quizEntities = Arrays.asList(
                QuizFixtures.getQuizEntity(),
                QuizFixtures.getQuizEntity()
        );

        BDDMockito.given(mockRepo.findAll())
                .willReturn(quizEntities);

        BDDMockito.given(mockMapper.mapToQuiz(Mockito.any(QuizEntity.class)))
                .willReturn(QuizFixtures.getQuiz());

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        List<Quiz> quizzes = quizService.getAllQuizzes();

        assertEquals(2, quizzes.size());
        Mockito.verify(mockRepo).findAll();
        Mockito.verify(mockMapper, Mockito.times(2)).mapToQuiz(Mockito.any(QuizEntity.class));

        System.out.println(quizzes);
    }

    @Test
    void givenQuizzes_whenFilterOutAllProperties_thenReturnQuizzesWithNullFields() {
        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        List<QuizEntity> quizEntities = Arrays.asList(
                QuizFixtures.getQuizEntity(),
                QuizFixtures.getQuizEntity()
        );

        BDDMockito.given(mockRepo.findAll())
                .willReturn(quizEntities);

        BDDMockito.given(mockMapper.mapToQuiz(Mockito.any(QuizEntity.class)))
                .willReturn(QuizFixtures.getQuiz());

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        List<Quiz> filteredQuizzes = quizService.getAllQuizzesWithFilterOutProperties(
                Arrays.asList("none")
        );

        filteredQuizzes.forEach(quiz -> {
            assertNull(quiz.getId());
            assertNull(quiz.getQuizCategory());
            assertNull(quiz.getDescription());
            assertNull(quiz.getQuestions());
        });

        System.out.println(filteredQuizzes);
    }

    @Test
    void givenQuizzes_whenFilterOutSpecificProperties_thenReturnQuizzesWithSelectedFieldsNull() {
        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        List<QuizEntity> quizEntities = Arrays.asList(
                QuizFixtures.getQuizEntity(),
                QuizFixtures.getQuizEntity()
        );

        BDDMockito.given(mockRepo.findAll())
                .willReturn(quizEntities);

        BDDMockito.given(mockMapper.mapToQuiz(Mockito.any(QuizEntity.class)))
                .willReturn(QuizFixtures.getQuiz());

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        List<Quiz> filteredQuizzes = quizService.getAllQuizzesWithFilterOutProperties(
                Arrays.asList("id", "description")
        );

        filteredQuizzes.forEach(quiz -> {
            assertNull(quiz.getId());
            assertNotNull(quiz.getQuizCategory());
            assertNull(quiz.getDescription());
            assertNotNull(quiz.getQuestions());
        });

        System.out.println(filteredQuizzes);
    }

    @Test
    void givenQuizzes_whenFilterWithEmptyList_thenReturnUnchangedQuizzes() {
        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        List<QuizEntity> quizEntities = Arrays.asList(
                QuizFixtures.getQuizEntity(),
                QuizFixtures.getQuizEntity()
        );

        BDDMockito.given(mockRepo.findAll())
                .willReturn(quizEntities);

        BDDMockito.given(mockMapper.mapToQuiz(Mockito.any(QuizEntity.class)))
                .willReturn(QuizFixtures.getQuiz());

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        List<Quiz> filteredQuizzes = quizService.getAllQuizzesWithFilterOutProperties(
                Collections.emptyList()
        );

        filteredQuizzes.forEach(quiz -> {
            assertNotNull(quiz.getId());
            assertNotNull(quiz.getQuizCategory());
            assertNotNull(quiz.getDescription());
            assertNotNull(quiz.getQuestions());
        });

        System.out.println(filteredQuizzes);
    }

    @Test
    void givenQuizzes_whenFilterWithUnknownProperties_thenReturnUnchangedQuizzes() {
        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        List<QuizEntity> quizEntities = Arrays.asList(
                QuizFixtures.getQuizEntity(),
                QuizFixtures.getQuizEntity()
        );

        BDDMockito.given(mockRepo.findAll())
                .willReturn(quizEntities);

        BDDMockito.given(mockMapper.mapToQuiz(Mockito.any(QuizEntity.class)))
                .willReturn(QuizFixtures.getQuiz());

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        List<Quiz> filteredQuizzes = quizService.getAllQuizzesWithFilterOutProperties(
                Arrays.asList("unknown", "nonexistent")
        );

        filteredQuizzes.forEach(quiz -> {
            assertNotNull(quiz.getId());
            assertNotNull(quiz.getQuizCategory());
            assertNotNull(quiz.getDescription());
            assertNotNull(quiz.getQuestions());
        });

        System.out.println(filteredQuizzes);

    }

    @Test
    void givenQuizWithoutQuestions_whenDeleteQuiz_thenQuizDeletedSuccessfully() {
        Long quizId = 1L;
        QuizEntity quizEntity = QuizFixtures.getQuizEntity();
        quizEntity.setId(quizId);
        quizEntity.setQuestions(Collections.emptySet());

        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        BDDMockito.given(mockRepo.findById(quizId))
                .willReturn(Optional.of(quizEntity));

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        assertDoesNotThrow(() -> quizService.deleteQuiz(quizId));

        Mockito.verify(mockRepo).findById(quizId);
        Mockito.verify(mockRepo).delete(quizEntity);
    }

    @Test
    void givenQuizWithQuestions_whenDeleteQuiz_thenThrowException() {
        Long quizId = 1L;
        QuizEntity quizEntity = QuizFixtures.getQuizEntity();
        quizEntity.setId(quizId);

        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        BDDMockito.given(mockRepo.findById(quizId))
                .willReturn(Optional.of(quizEntity));

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            quizService.deleteQuiz(quizId);
        });

        assertEquals("Cannot delete quiz with questions", exception.getMessage());
        Mockito.verify(mockRepo).findById(quizId);
        Mockito.verify(mockRepo, Mockito.never()).delete(quizEntity);
    }

    @Test
    void givenNonExistingQuiz_whenDeleteQuiz_thenThrowException() {
        Long quizId = 1L;
        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        BDDMockito.given(mockRepo.findById(quizId))
                .willReturn(Optional.empty());

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            quizService.deleteQuizSafely(quizId);
        });

        assertEquals("Quiz not found", exception.getMessage());
        Mockito.verify(mockRepo).findById(quizId);
        Mockito.verify(mockRepo, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void givenQuizWithQuestions_whenDeleteQuizSafely_thenQuizDeletedSuccessfully() {
        Long quizId = 1L;
        QuizEntity quizEntity = QuizFixtures.getQuizEntity();
        quizEntity.setId(quizId);
        quizEntity.setQuestions(Set.of(new QuestionEntity()));

        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        BDDMockito.given(mockRepo.findById(quizId))
                .willReturn(Optional.of(quizEntity));

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        assertDoesNotThrow(() -> quizService.deleteQuizSafely(quizId));

        Mockito.verify(mockRepo).findById(quizId);
        Mockito.verify(mockRepo).delete(quizEntity);
    }

    @Test
    void givenNonExistingQuiz_whenDeleteQuizSafely_thenThrowException() {
        Long quizId = 1L;

        var mockRepo = Mockito.mock(QuizRepository.class);
        var mockMapper = Mockito.mock(QuizMapper.class);

        BDDMockito.given(mockRepo.findById(quizId))
                .willReturn(Optional.empty());

        QuizService quizService = new QuizService(mockRepo, mockMapper);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            quizService.deleteQuizSafely(quizId);
        });

        assertEquals("Quiz not found", exception.getMessage());

        Mockito.verify(mockRepo).findById(quizId);
        Mockito.verify(mockRepo, Mockito.never()).delete(Mockito.any());
    }
}