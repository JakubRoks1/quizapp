package com.example.quizapp.service;

import com.example.quizapp.model.Question;
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
        Question question = new Question();
        when(questionRepository.save(question)).thenReturn(question);

        // When
        Question result = questionService.addQuestion(question);

        // Then
        assertThat(result).isEqualTo(question);
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    public void testGetAllQuestions() {
        // Given
        Question question1 = new Question();
        Question question2 = new Question();
        List<Question> questions = Arrays.asList(question1, question2);
        when(questionRepository.findAll()).thenReturn(questions);

        // When
        List<Question> result = questionService.getAllQuestions();

        // Then
        assertThat(result).isEqualTo(questions);
        verify(questionRepository, times(1)).findAll();
    }

    @Test
    public void testGetQuestionById() {
        // Given
        Long id = 1L;
        Question question = new Question();
        when(questionRepository.findById(id)).thenReturn(Optional.of(question));

        // When
        Optional<Question> result = questionService.getQuestionById(id);

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
        Optional<Question> result = questionService.getQuestionById(id);

        // Then
        assertThat(result).isNotPresent();
        verify(questionRepository, times(1)).findById(id);
    }

    @Test
    public void testUpdateQuestion() {
        // Given
        Long id = 1L;
        Question existingQuestion = new Question();
        Question updatedDetails = new Question();
        updatedDetails.setQuestionText("Updated text");
        updatedDetails.setAnswer("Updated answer");

        when(questionRepository.findById(id)).thenReturn(Optional.of(existingQuestion));
        when(questionRepository.save(existingQuestion)).thenReturn(existingQuestion);

        // When
        Question result = questionService.updateQuestion(id, updatedDetails);

        // Then
        assertThat(result.getQuestionText()).isEqualTo("Updated text");
        assertThat(result.getAnswer()).isEqualTo("Updated answer");
        verify(questionRepository, times(1)).findById(id);
        verify(questionRepository, times(1)).save(existingQuestion);
    }

    @Test
    public void testUpdateQuestion_NotFound() {
        // Given
        Long id = 1L;
        Question updatedDetails = new Question();
        when(questionRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        try {
            questionService.updateQuestion(id, updatedDetails);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Question not found");
        }
        verify(questionRepository, times(1)).findById(id);
        verify(questionRepository, times(0)).save(any(Question.class));
    }

    @Test
    public void testDeleteQuestion() {
        // Given
        Long id = 1L;
        Question question = new Question();
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
        verify(questionRepository, times(0)).delete(any(Question.class));
    }
}
