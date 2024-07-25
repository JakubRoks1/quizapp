package com.example.quizapp.service;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddQuestion() {
        // Given
        QuestionEntity question = new QuestionEntity();
        when(questionRepository.save(question)).thenReturn(question);

        // When
        QuestionEntity result = questionService.addQuestion(question);

        // Then
        assertThat(result).isEqualTo(question);
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    public void testGetAllQuestions() {
        // Given
        QuestionEntity question1 = new QuestionEntity();
        QuestionEntity question2 = new QuestionEntity();
        List<QuestionEntity> questions = Arrays.asList(question1, question2);
        when(questionRepository.findAll()).thenReturn(questions);

        // When
        List<QuestionEntity> result = questionService.getAllQuestions();

        // Then
        assertThat(result).isEqualTo(questions);
        verify(questionRepository, times(1)).findAll();
    }

    @Test
    public void testGetQuestionById() {
        // Given
        Long id = 1L;
        QuestionEntity question = new QuestionEntity();
        when(questionRepository.findById(id)).thenReturn(Optional.of(question));

        // When
        Optional<QuestionEntity> result = questionService.getQuestionById(id);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(question);
        verify(questionRepository, times(1)).findById(id);
    }

    @Test
    public void testGetQuestionById_NotFound() {
        // Given
        Long id = 1L;
        when(questionRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<QuestionEntity> result = questionService.getQuestionById(id);

        // Then
        assertThat(result).isNotPresent();
        verify(questionRepository, times(1)).findById(id);
    }

    @Test
    public void testUpdateQuestion() {
        // Given
        Long id = 1L;
        QuestionEntity existingQuestion = new QuestionEntity();
        QuestionEntity updatedDetails = new QuestionEntity();
        updatedDetails.setQuestionText("testText");
        updatedDetails.setAnswer("testAnswer");

        when(questionRepository.findById(id)).thenReturn(Optional.of(existingQuestion));
        when(questionRepository.save(existingQuestion)).thenReturn(existingQuestion);

        // When
        QuestionEntity result = questionService.updateQuestion(id, updatedDetails);

        // Then
        assertThat(result.getQuestionText()).isEqualTo("testText");
        assertThat(result.getAnswer()).isEqualTo("testAnswer");
        verify(questionRepository, times(1)).findById(id);
        verify(questionRepository, times(1)).save(existingQuestion);
    }

    @Test
    public void testUpdateQuestion_NotFound() {
        // Given
        Long id = 1L;
        QuestionEntity updatedDetails = new QuestionEntity();
        when(questionRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        try {
            questionService.updateQuestion(id, updatedDetails);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Question not found");
        }
        verify(questionRepository, times(1)).findById(id);
        verify(questionRepository, times(0)).save(any(QuestionEntity.class));
    }

    @Test
    public void testDeleteQuestion() {
        // Given
        Long id = 1L;
        QuestionEntity question = new QuestionEntity();
        when(questionRepository.findById(id)).thenReturn(Optional.of(question));

        // When
        questionService.deleteQuestion(id);

        // Then
        verify(questionRepository, times(1)).findById(id);
        verify(questionRepository, times(1)).delete(question);
    }

    @Test
    public void testDeleteQuestion_NotFound() {
        // Given
        Long id = 1L;
        when(questionRepository.findById(id)).thenReturn(Optional.empty());

        // When
        questionService.deleteQuestion(id);

        // Then
        verify(questionRepository, times(1)).findById(id);
        verify(questionRepository, times(0)).delete(any(QuestionEntity.class));
    }
}
