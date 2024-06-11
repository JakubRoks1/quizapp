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
}
