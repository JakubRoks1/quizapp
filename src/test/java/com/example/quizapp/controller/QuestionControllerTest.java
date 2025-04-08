package com.example.quizapp.controller;

import com.example.quizapp.exception.ExceptionType;
import com.example.quizapp.fixtures.QuizFixtures;
import com.example.quizapp.json.QuestionJson;
import com.example.quizapp.mappers.ErrorMapper;
import com.example.quizapp.mappers.ErrorMapperImpl;
import com.example.quizapp.mappers.QuestionMapper;
import com.example.quizapp.mappers.QuestionMapperImpl;
import com.example.quizapp.model.Question;
import com.example.quizapp.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import com.fasterxml.jackson.core.type.TypeReference;


/**
 * ===========================
 * |        Test Name        | Naming Convention | AssertJ Used | Success |
 * ===========================
 * | QuestionControllerTest
 *
 * | getQuestionById_shouldReturnQuestion                  |   | X | X |
 * | deleteQuestion_shouldReturnNOk                        |   |   |   |
 * | createQuestion_shouldReturnCreatedQuestion           |   |   |   |
 * | getAllQuestions_shouldReturnListOfQuestions         |   |   |   |
 * | getSpecified_shouldReturnFilteredQuestions          |   |   |   |
 * | getQuestionById_QuestionNotFound_ShouldReturn404    |   |   |   |
 * | updateQuestion_shouldReturnUpdatedQuestion          |   |   |   |
 * -------------------------------------------------------
 * | QuizControllerTest
 *
 * | delete                                           |   |   |   |
 * | getSpecified_shouldReturnFilteredQuizzes()      |   |   |   |
 * | templateTest()                                  |   |   |   |
 * | getAllQuizzes_countMode_shouldReturnQuizzesWithQuestionCount() |   |   |   |
 * | deleteQuiz_shouldReturnOkStatus()              |   |   |   |
 * | getById()                                      |   |   |   |
 * | createQuiz_shouldReturnCreatedQuiz()           |   |   |   |
 * | getQuizById_notFound_shouldReturnNotFound()    |   |   |   |
 * | createQuiz_withProvidedId_shouldReturnBadRequest |   |   |   |
 * -------------------------------------------------------
 * | QuizAppExceptionTest
 *
 * | constructor_shouldSetAllFields                |   |   |   |
 * -------------------------------------------------------
 * | QuizMapperImplTest
 *
 * | givenQuizEntity_whenMapToQuiz_thenCorrect    |   |   |   |
 * -------------------------------------------------------
 * | QuestionServiceTest
 *
 * | givenQuestion()                                    |   |   |   |
 * | updateQuestion_WhenDoesNotExist_ShouldThrowException() |   |   |   |
 * | addQuestion_ShouldReturnSavedQuestion()          |   |   |   |
 * | getAllQuestionsWithFilterOutProperties_ShouldFilterSpecifiedFields() |   |   |   |
 * | updateQuestion_WhenExists_ShouldReturnUpdatedQuestion() |   |   |   |
 * | getAllQuestions_ShouldReturnAllQuestions()       |   |   |   |
 * | deleteQuestion_WhenExists_ShouldDeleteSuccessfully() |   |   |   |
 * | findById_WhenExists_ShouldReturnQuestion()       |   |   |   |
 * | deleteQuestion_WhenDoesNotExist_ShouldThrowException() |   |   |   |
 * | getQuestion_WhenExists_ShouldReturnQuestion()    |   |   |   |
 * | findById_WhenDoesNotExist_ShouldThrowException() |   |   |   |
 * | getAllQuestionsWithFilteredProperties_ShouldFilterSpecifiedFields() |   |   |   |
 * -------------------------------------------------------
 * | QuizServiceTest
 *
 * | addQuiz_ShouldSaveAndReturnQuiz()           |   |   |   |
 * | deleteQuizWithQuestions_ShouldThrowException() |   |   |   |
 * | addQuizWithNullValues_ShouldSaveWithNullValues() |   |   |   |
 * | updateExistingQuiz_ShouldUpdateSuccessfully() |   |   |   |
 * | getQuizWithoutQuestions_ShouldReturnQuizWithoutQuestions() |   |   |   |
 * | getQuizWithQuestions_ShouldReturnQuizWithQuestions() |   |   |   |
 * | deleteQuizWithoutQuestions_ShouldDeleteSuccessfully() |   |   |   |
 * | addQuiz_WhenRepositoryThrowsException_ShouldPropagateException() |   |   |   |
 * | getAllQuizzes_WithMultipleQuizzes_ShouldReturnAllQuizzes() |   |   |   |
 * | updateNonExistingQuiz_ShouldThrowException() |   |   |   |
 * | getAllQuizzes_WhenEmpty_ShouldReturnEmptyList() |   |   |   |
 * | deleteNonExistingQuiz_ShouldReturnFalse()      |   |   |   |
 * -------------------------------------------------------
 * | QuizAppApplicationTest
 *
 * | contextLoads()                                  |   |   |   |
 * ===========================
 */

@WebMvcTest(QuestionController.class)
@Import({QuestionMapperImpl.class, ErrorMapperImpl.class, ErrorControllerAdvice.class})  // Import the generated mapper implementation
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private ErrorMapper errorMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenQuestions_whenGetAllQuestions_thenShouldReturnListOfQuestions() throws Exception {
        Question testQuestion = QuizFixtures.getQuiz().getQuestions().stream().findFirst().orElseThrow();
        when(questionService.getAllQuestions()).thenReturn(List.of(testQuestion));

        mockMvc.perform(get("/questions"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(testQuestion.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].questionText").value(testQuestion.getQuestionText()));
    }

    @Test
    void givenQuestionId_whenGetQuestionById_thenShouldReturnQuestion() throws Exception {
        Question testQuestion = QuizFixtures.getQuiz().getQuestions().stream().findFirst().orElseThrow();
        when(questionService.getQuestion(testQuestion.getId())).thenReturn(Optional.of(testQuestion));

        MvcResult result = mockMvc.perform(get("/questions/{id}", testQuestion.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content)
                .contains("\"id\":" + testQuestion.getId())
                .contains("\"questionText\":\"" + testQuestion.getQuestionText() + "\"");

        verify(questionService).getQuestion(testQuestion.getId());
    }

    @Test
    void givenQuestionJson_whenCreateQuestion_thenShouldReturnCreatedQuestion() throws Exception {
        QuestionJson questionJson = new QuestionJson();
        questionJson.setQuestionText("Test question");

        Question testQuestion = QuizFixtures.getQuiz().getQuestions().stream().findFirst().orElseThrow();
        when(questionService.addQuestion(any(Question.class))).thenReturn(testQuestion);

        mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(questionJson)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testQuestion.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionText").value(testQuestion.getQuestionText()));
    }

    @Test
    void givenQuestionJson_whenUpdateQuestion_thenShouldReturnUpdatedQuestion() throws Exception {
        QuestionJson questionJson = new QuestionJson();
        questionJson.setQuestionText("Updated question");

        Question testQuestion = QuizFixtures.getQuiz().getQuestions().stream().findFirst().orElseThrow();
        when(questionService.updateQuestion(eq(testQuestion.getId()), any(Question.class))).thenReturn(testQuestion);

        mockMvc.perform(put("/questions/{id}", testQuestion.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(questionJson)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testQuestion.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionText").value(testQuestion.getQuestionText()));
    }

    @Test
    void givenQuestionId_whenDeleteQuestion_thenShouldReturnNotFound() throws Exception {
        Question testQuestion = QuizFixtures.getQuiz().getQuestions().stream().findFirst().orElseThrow();
        when(questionService.getQuestion(testQuestion.getId())).thenThrow(ExceptionType.QUESTION_NOT_FOUND.getExceptionWithBody());

        var result = mockMvc.perform(delete("/questions/{id}", testQuestion.getId()))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(404);

        String responseBody = result.getResponse().getContentAsString();
        System.out.println(responseBody);

        if (responseBody.isEmpty()) {
            System.out.println("Response body is empty.");
        } else {
            assertThat(responseBody)
                    .contains("Question not found")
                    .contains("ER-002");
        }

        verify(questionService).getQuestion(testQuestion.getId());
        verify(questionService, never()).deleteQuestion(testQuestion.getId());
    }

    @Test
    void givenNonExistingQuestionId_whenGetQuestionById_thenShouldReturn404() throws Exception {
        Long nonExistQuestionId = 999L;
        Mockito.when(questionService.getQuestion(nonExistQuestionId)).thenThrow(ExceptionType.QUESTION_NOT_FOUND.getExceptionWithBody());

        mockMvc.perform(get("/questions/{id}", nonExistQuestionId))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpectAll(
                        status().isNotFound(),
                        MockMvcResultMatchers.jsonPath("$.message").value("Question not found"),
                        MockMvcResultMatchers.jsonPath("$.errorCode").value("ER-002"),
                        MockMvcResultMatchers.jsonPath("$.timestamp").exists()
                );

        verify(questionService).getQuestion(nonExistQuestionId);
    }

    @Test
    void givenFields_whenGetSpecifiedQuestions_thenShouldReturnFilteredQuestions() throws Exception {
        Question testQuestion = QuizFixtures.getQuiz().getQuestions().stream().findFirst().orElseThrow();
        List<Question> questions = List.of(testQuestion);
        List<String> fields = List.of("id", "questionText");

        when(questionService.getAllQuestionsWithFilterOutProperties(fields)).thenReturn(questions);

        mockMvc.perform(get("/questions/GetByFields")
                        .param("fields", "id", "questionText"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(testQuestion.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].questionText").value(testQuestion.getQuestionText()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].answerCount").doesNotExist());
    }
}



