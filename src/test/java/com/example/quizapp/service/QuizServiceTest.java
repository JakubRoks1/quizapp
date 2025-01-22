package com.example.quizapp.service;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.entity.QuizEntity;
import com.example.quizapp.fixtures.QuizFixtures;
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

import static org.junit.jupiter.api.Assertions.*;
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

        quiz = QuizFixtures.givenQuizWithoutQuestions();
        quizEntity = quizMapper.mapToQuizEntity(quiz);
    }

    @Test
    void getQuizWithQuestions_ShouldReturnQuizWithQuestions() {
        Question question = new Question();
        question.setId(1L);
        quiz.setQuestions(Set.of(question));

        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(1L);
        quizEntity.setQuestions(Set.of(questionEntity));

        when(quizRepository.findByIdWithQuestions(quizId)).thenReturn(Optional.of(quizEntity));

        Optional<Quiz> result = quizService.getQuiz(quizId, true);

        assertTrue(result.isPresent());
        assertEquals(quizEntity.getId(), result.get().getId());
        assertEquals(quizEntity.getQuestions().size(), result.get().getQuestions().size());

        System.out.println(result);
    }

    @Test
    void getQuizWithoutQuestions_ShouldReturnQuizWithoutQuestions() {
        QuizEntity entityWithoutQuestions = quizMapper.mapToQuizEntity(quiz);
        entityWithoutQuestions.setQuestions(Set.of());

        when(quizRepository.findByIdWithoutQuestions(quizId)).thenReturn(Optional.of(entityWithoutQuestions));

        Optional<Quiz> result = quizService.getQuiz(quizId, false);

        assertTrue(result.isPresent());
        assertEquals(entityWithoutQuestions.getId(), result.get().getId());
        assertTrue(result.get().getQuestions().isEmpty());

        System.out.println(result);
    }

    @Test
    void addQuiz_ShouldSaveAndReturnQuiz() {
        when(quizRepository.save(any(QuizEntity.class))).thenReturn(quizEntity);

        Quiz result = quizService.addQuiz(quiz);

        assertNotNull(result);
        assertEquals("Test Category", result.getQuizCategory());
        assertEquals("Test Description", result.getDescription());
        verify(quizRepository).save(any(QuizEntity.class));

        System.out.println(result);
    }

    @Test
    void addQuizWithNullValues_ShouldSaveWithNullValues() {
        Quiz emptyQuiz = new Quiz();
        QuizEntity emptyEntity = quizMapper.mapToQuizEntity(emptyQuiz);
        emptyEntity.setId(quizId);

        when(quizRepository.save(any(QuizEntity.class))).thenReturn(emptyEntity);

        Quiz result = quizService.addQuiz(emptyQuiz);

        assertNotNull(result);
        assertNull(result.getQuizCategory());
        assertNull(result.getDescription());

        System.out.println(result);
    }

    @Test
    void addQuiz_WhenRepositoryThrowsException_ShouldPropagateException() {
        when(quizRepository.save(any(QuizEntity.class))).thenThrow(new RuntimeException("Database save error"));

        assertThrows(RuntimeException.class, () -> quizService.addQuiz(quiz));
    }

    @Test
    void updateExistingQuiz_ShouldUpdateSuccessfully() {
        Quiz updateRequest = new Quiz();
        updateRequest.setQuizCategory("New Category");
        updateRequest.setDescription("New Description");

        QuizEntity existingEntity = quizMapper.mapToQuizEntity(quiz);
        QuizEntity updatedEntity = quizMapper.mapToQuizEntity(updateRequest);
        updatedEntity.setId(quizId);

        when(quizRepository.findById(quizId)).thenReturn(Optional.of(existingEntity));
        when(quizRepository.save(any(QuizEntity.class))).thenReturn(updatedEntity);

        Quiz result = quizService.updateQuiz(quizId, updateRequest);

        assertNotNull(result);
        assertEquals("New Category", result.getQuizCategory());
        assertEquals("New Description", result.getDescription());
        verify(quizRepository).findById(quizId);
        verify(quizRepository).save(any(QuizEntity.class));

        System.out.println(result);
    }

    @Test
    void updateNonExistingQuiz_ShouldThrowException() {
        Quiz updateRequest = new Quiz();
        when(quizRepository.findById(quizId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> quizService.updateQuiz(quizId, updateRequest));

        assertEquals("nie-ma", exception.getMessage());
        verify(quizRepository).findById(quizId);
        verify(quizRepository, never()).save(any());
    }

    @Test
    void getAllQuizzes_WhenEmpty_ShouldReturnEmptyList() {
        when(quizRepository.findAll()).thenReturn(Collections.emptyList());

        List<Quiz> result = quizService.getAllQuizzes();

        assertTrue(result.isEmpty());
        verify(quizRepository).findAll();

        System.out.println(result);
    }

    @Test
    void getAllQuizzes_WithMultipleQuizzes_ShouldReturnAllQuizzes() {
        List<QuizEntity> entities = Arrays.asList(quizEntity, quizEntity);
        when(quizRepository.findAll()).thenReturn(entities);

        List<Quiz> result = quizService.getAllQuizzes();

        assertEquals(2, result.size());
        verify(quizRepository).findAll();

        System.out.println(result);
    }

    @Test
    void deleteQuizWithoutQuestions_ShouldDeleteSuccessfully() {
        QuizEntity emptyQuizEntity = quizMapper.mapToQuizEntity(quiz);
        emptyQuizEntity.setId(quizId);
        emptyQuizEntity.setQuestions(Collections.emptySet());

        when(quizRepository.findByIdWithQuestions(quizId)).thenReturn(Optional.of(emptyQuizEntity));

        boolean result = quizService.deleteQuiz(quizId);

        assertTrue(result);
        verify(quizRepository).findByIdWithQuestions(quizId);
        verify(quizRepository).delete(emptyQuizEntity);

        System.out.println(result);
    }

    @Test
    void deleteQuizWithQuestions_ShouldThrowException() {
        QuizEntity quizWithQuestions = quizMapper.mapToQuizEntity(quiz);
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(1L);
        quizWithQuestions.setQuestions(Set.of(questionEntity));

        when(quizRepository.findByIdWithQuestions(quizId)).thenReturn(Optional.of(quizWithQuestions));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> quizService.deleteQuiz(quizId));

        assertEquals("Cannot delete quiz with questions", exception.getMessage());
        verify(quizRepository, never()).delete(any());
    }

    @Test
    void deleteNonExistingQuiz_ShouldReturnFalse() {
        when(quizRepository.findByIdWithQuestions(quizId)).thenReturn(Optional.empty());

        boolean result = quizService.deleteQuiz(quizId);

        assertFalse(result);
        verify(quizRepository, never()).delete(any());

        System.out.println(result);
    }

    // postaraj się użyć QuizMappera prawdziwego testach
    // controller na questiony + test (metoda rest + testy)
}
