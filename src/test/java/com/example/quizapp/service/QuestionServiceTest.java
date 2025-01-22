package com.example.quizapp.service;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.fixtures.QuestionFixtures;
import com.example.quizapp.mappers.QuestionMapper;
import com.example.quizapp.model.Question;
import com.example.quizapp.repository.QuestionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("service")
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionMapper questionMapper;

    @InjectMocks
    private QuestionService questionService;

    private Question question;
    private QuestionEntity questionEntity;

    @BeforeEach
    void setUp() {
        question = QuestionFixtures.getQuestion();
        questionEntity = QuestionFixtures.getQuestionEntity();
    }

    @Test
    void addQuestion_ShouldReturnSavedQuestion() {
        when(questionMapper.mapToQuestionEntity(question)).thenReturn(questionEntity);
        when(questionRepository.save(questionEntity)).thenReturn(questionEntity);
        when(questionMapper.mapToQuestion(questionEntity)).thenReturn(question);

        Question result = questionService.addQuestion(question);

        assertNotNull(result);
        assertEquals(question.getId(), result.getId());
        assertEquals(question.getQuestionText(), result.getQuestionText());
        verify(questionRepository).save(any(QuestionEntity.class));

        System.out.println(result);
    }

    @Test
    void getAllQuestions_ShouldReturnAllQuestions() {
        List<QuestionEntity> questionEntities = Arrays.asList(questionEntity);
        when(questionRepository.findAll()).thenReturn(questionEntities);
        when(questionMapper.mapToQuestion(questionEntity)).thenReturn(question);

        List<Question> result = questionService.getAllQuestions();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(question.getId(), result.get(0).getId());
        verify(questionRepository).findAll();
        System.out.println(result);
    }

    @Test
    void getQuestion_WhenExists_ShouldReturnQuestion() {
        when(questionRepository.findById(1L)).thenReturn(Optional.of(questionEntity));
        when(questionMapper.mapToQuestion(questionEntity)).thenReturn(question);

        Optional<Question> result = questionService.getQuestion(1L);

        assertTrue(result.isPresent());
        assertEquals(question.getId(), result.get().getId());
        verify(questionRepository).findById(1L);

        System.out.println(result);
    }

    @Test
    void getQuestion_WhenDoesNotExist_ShouldReturnEmpty() {
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Question> result = questionService.getQuestion(1L);

        assertFalse(result.isPresent());
        verify(questionRepository).findById(1L);

        System.out.println(result);
    }

    @Test
    void updateQuestion_WhenExists_ShouldReturnUpdatedQuestion() {
        when(questionRepository.findById(1L)).thenReturn(Optional.of(questionEntity));
        when(questionRepository.save(questionEntity)).thenReturn(questionEntity);
        when(questionMapper.mapToQuestion(questionEntity)).thenReturn(question);

        Question result = questionService.updateQuestion(1L, question);

        assertNotNull(result);
        assertEquals(question.getId(), result.getId());
        assertEquals(question.getQuestionText(), result.getQuestionText());
        verify(questionRepository).save(questionEntity);

        System.out.println(result);
    }

    @Test
    void updateQuestion_WhenDoesNotExist_ShouldThrowException() {
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> questionService.updateQuestion(1L, question));
        verify(questionRepository, never()).save(any(QuestionEntity.class));

    }

    @Test
    void deleteQuestion_WhenExists_ShouldDeleteSuccessfully() {
        when(questionRepository.existsById(1L)).thenReturn(true);

        questionService.deleteQuestion(1L);

        verify(questionRepository).deleteById(1L);
    }

    @Test
    void deleteQuestion_WhenDoesNotExist_ShouldThrowException() {
        when(questionRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> questionService.deleteQuestion(1L));
        verify(questionRepository, never()).deleteById(any());
    }

    @Test
    void findById_WhenExists_ShouldReturnQuestion() {
        when(questionRepository.findById(1L)).thenReturn(Optional.of(questionEntity));
        when(questionMapper.mapToQuestion(questionEntity)).thenReturn(question);

        Question result = questionService.findById(1L);

        assertNotNull(result);
        assertEquals(question.getId(), result.getId());
        verify(questionRepository).findById(1L);

        System.out.println(result);
    }

    @Test
    void findById_WhenDoesNotExist_ShouldThrowException() {
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> questionService.findById(1L));
    }

    @Test
    void getAllQuestionsWithFilteredProperties_ShouldFilterSpecifiedFields() {
        List<QuestionEntity> questionEntities = Arrays.asList(questionEntity);
        when(questionRepository.findAll()).thenReturn(questionEntities);
        when(questionMapper.mapToQuestion(questionEntity)).thenReturn(question);

        List<Question> result = questionService.getAllQuestionsWithFilteredProperties(Arrays.asList("questionText"));

        Assertions.assertThat(result)
            .isNotNull()
            .hasSize(1);

        Assertions.assertThat(result.get(0))
                .satisfies(question1 -> {
                    Assertions.assertThat(question1.getQuestionText()).isNull();
                    Assertions.assertThat(question1.getId()).isEqualTo(questionEntity.getId());
                });
        System.out.println(result);
    }

    @Test
    void getAllQuestionsWithFilterOutProperties_ShouldFilterSpecifiedFields() {
        List<QuestionEntity> questionEntities = Arrays.asList(questionEntity);
        when(questionRepository.findAll()).thenReturn(questionEntities);
        when(questionMapper.mapToQuestion(questionEntity)).thenReturn(question);

        List<Question> result = questionService.getAllQuestionsWithFilterOutProperties(Arrays.asList("id"));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertNull(result.get(0).getId());
        assertNotNull(result.get(0).getQuestionText());

        System.out.println(result);
    }

    void givenQuestion() {

    }
}
