package com.example.quizapp.service;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.exception.ExceptionType;
import com.example.quizapp.exception.QuizAppException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void givenValidQuestion_whenAddQuestion_thenShouldReturnSavedQuestion() {
        when(questionMapper.mapToQuestionEntity(question)).thenReturn(questionEntity);
        when(questionRepository.save(questionEntity)).thenReturn(questionEntity);
        when(questionMapper.mapToQuestion(questionEntity)).thenReturn(question);

        Question result = questionService.addQuestion(question);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(question.getId());
        Assertions.assertThat(result.getQuestionText()).isEqualTo(question.getQuestionText());
        verify(questionRepository).save(any(QuestionEntity.class));
    }

    @Test
    void givenQuestionsExist_whenGetAllQuestions_thenShouldReturnAllQuestions() {
        List<QuestionEntity> questionEntities = Arrays.asList(questionEntity);
        when(questionRepository.findAll()).thenReturn(questionEntities);
        when(questionMapper.mapToQuestion(questionEntity)).thenReturn(question);

        List<Question> result = questionService.getAllQuestions();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0).getId()).isEqualTo(question.getId());
        verify(questionRepository).findAll();
    }

    @Test
    void givenQuestionExists_whenGetQuestion_thenShouldReturnQuestion() {
        when(questionRepository.findById(1L)).thenReturn(Optional.of(questionEntity));
        when(questionMapper.mapToQuestion(questionEntity)).thenReturn(question);

        Optional<Question> result = questionService.getQuestion(1L);

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getId()).isEqualTo(question.getId());
        verify(questionRepository).findById(1L);
    }

    @Test
    void givenQuestionDoesNotExist_whenGetQuestion_thenShouldThrowException() {
        when(questionRepository.findById(1L)).thenReturn(Optional.of(questionEntity));
        when(questionMapper.mapToQuestion(questionEntity)).thenReturn(question);

        Optional<Question> result = questionService.getQuestion(1L);

        assertTrue(result.isPresent());
        assertEquals(question.getId(), result.get().getId());
        verify(questionRepository).findById(1L);

        System.out.println(result);
    }

    @Test
    void givenExistingQuestion_whenUpdateQuestion_thenShouldReturnUpdatedQuestion() {
        when(questionRepository.findById(1L)).thenReturn(Optional.of(questionEntity));
        when(questionRepository.save(questionEntity)).thenReturn(questionEntity);
        when(questionMapper.mapToQuestion(questionEntity)).thenReturn(question);

        Question result = questionService.updateQuestion(1L, question);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(question.getId());
        Assertions.assertThat(result.getQuestionText()).isEqualTo(question.getQuestionText());
        verify(questionRepository).save(questionEntity);
    }

    @Test
    void givenNonExistingQuestion_whenUpdateQuestion_thenShouldThrowException() {
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> questionService.updateQuestion(1L, question))
                .isInstanceOf(QuizAppException.class)
                .hasMessageContaining(ExceptionType.QUESTION_NOT_FOUND.getMessage());

        verify(questionRepository, never()).save(any(QuestionEntity.class));
    }

    @Test
    void givenExistingQuestion_whenDeleteQuestion_thenShouldDeleteSuccessfully() {
        when(questionRepository.existsById(1L)).thenReturn(true);

        questionService.deleteQuestion(1L);

        verify(questionRepository).deleteById(1L);
    }

    @Test
    void givenNonExistingQuestion_whenDeleteQuestion_thenShouldThrowException() {
        when(questionRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThatThrownBy(() -> questionService.deleteQuestion(1L))
                .isInstanceOf(QuizAppException.class)
                .hasMessageContaining(ExceptionType.QUESTION_NOT_FOUND.getMessage());

        verify(questionRepository, never()).deleteById(any());
    }

    @Test
    void givenQuestionExists_whenFindById_thenShouldReturnQuestion() {
        when(questionRepository.findById(1L)).thenReturn(Optional.of(questionEntity));
        when(questionMapper.mapToQuestion(questionEntity)).thenReturn(question);

        Question result = questionService.findById(1L);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(question.getId());
        verify(questionRepository).findById(1L);
    }

    @Test
    void givenQuestionsExist_whenGetAllQuestionsWithFilteredProperties_thenShouldFilterSpecifiedFields() {
        List<QuestionEntity> questionEntities = Arrays.asList(questionEntity);
        when(questionRepository.findAll()).thenReturn(questionEntities);
        when(questionMapper.mapToQuestion(questionEntity)).thenReturn(question);

        List<Question> result = questionService.getAllQuestionsWithFilteredProperties(Arrays.asList("id"));

        Assertions.assertThat(result)
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(result.get(0))
                .satisfies(question1 -> {
                    Assertions.assertThat(question1.getQuestionText()).isNull();
                    Assertions.assertThat(question1.getId()).isNotNull();
                });
    }

    @Test
    void givenQuestionsExist_whenGetAllQuestionsWithFilterOutProperties_thenShouldFilterSpecifiedFields() {
        List<QuestionEntity> questionEntities = Arrays.asList(questionEntity);
        when(questionRepository.findAll()).thenReturn(questionEntities);
        when(questionMapper.mapToQuestion(questionEntity)).thenReturn(question);

        List<Question> result = questionService.getAllQuestionsWithFilterOutProperties(Arrays.asList("id"));

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0).getId()).isNull();
        Assertions.assertThat(result.get(0).getQuestionText()).isNotNull();
    }
}
