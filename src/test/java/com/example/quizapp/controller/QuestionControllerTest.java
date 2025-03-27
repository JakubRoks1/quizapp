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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    void getAllQuestions_shouldReturnListOfQuestions() throws Exception {
        Question testQuestion = QuizFixtures.getQuiz().getQuestions().stream().findFirst().orElseThrow();
        when(questionService.getAllQuestions()).thenReturn(List.of(testQuestion));

        mockMvc.perform(get("/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testQuestion.getId()))
                .andExpect(jsonPath("$[0].questionText").value(testQuestion.getQuestionText()));
    }

    @Test
    void getQuestionById_shouldReturnQuestion() throws Exception {
        Question testQuestion = QuizFixtures.getQuiz().getQuestions().stream().findFirst().orElseThrow();
        when(questionService.getQuestion(testQuestion.getId())).thenReturn(Optional.of(testQuestion));

        mockMvc.perform(get("/questions/{id}", testQuestion.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testQuestion.getId()))
                .andExpect(jsonPath("$.questionText").value(testQuestion.getQuestionText()));

        verify(questionService).getQuestion(testQuestion.getId());
    }

    @Test
    void createQuestion_shouldReturnCreatedQuestion() throws Exception {
        QuestionJson questionJson = new QuestionJson();
        questionJson.setQuestionText("Test question");

        Question testQuestion = QuizFixtures.getQuiz().getQuestions().stream().findFirst().orElseThrow();
        when(questionService.addQuestion(any(Question.class))).thenReturn(testQuestion);

        mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(questionJson)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testQuestion.getId()))
                .andExpect(jsonPath("$.questionText").value(testQuestion.getQuestionText()));
    }

    @Test
    void updateQuestion_shouldReturnUpdatedQuestion() throws Exception {
        QuestionJson questionJson = new QuestionJson();
        questionJson.setQuestionText("Updated question");

        Question testQuestion = QuizFixtures.getQuiz().getQuestions().stream().findFirst().orElseThrow();
        when(questionService.updateQuestion(eq(testQuestion.getId()), any(Question.class))).thenReturn(testQuestion);

        mockMvc.perform(put("/questions/{id}", testQuestion.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(questionJson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testQuestion.getId()))
                .andExpect(jsonPath("$.questionText").value(testQuestion.getQuestionText()));
    }

    @Test
    void deleteQuestion_shouldReturnNOk() throws Exception {
        Question testQuestion = QuizFixtures.getQuiz().getQuestions().stream().findFirst().orElseThrow();
        doThrow(ExceptionType.QUESTION_NOT_FOUND.getException()).when(questionService).deleteQuestion(testQuestion.getId());

        mockMvc.perform(delete("/questions/{id}", testQuestion.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Question successfully deleted"));

        verify(questionService).deleteQuestion(testQuestion.getId());
    }

    @Test
    void getQuestionById_QuestionNotFound_ShouldReturn404() throws Exception {
        Long nonExistQuestionId = 999L;
        Mockito.when(questionService.getQuestion(nonExistQuestionId)).thenThrow(ExceptionType.QUESTION_NOT_FOUND.getExceptionWithBody());
//        doThrow(ExceptionType.QUESTION_NOT_FOUND.getExceptionWithBody()).when(questionService).getQuestion(nonExistQuestionId);
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
    void getSpecified_shouldReturnFilteredQuestions() throws Exception {
        Question testQuestion = QuizFixtures.getQuiz().getQuestions().stream().findFirst().orElseThrow();
        List<Question> questions = List.of(testQuestion);
        List<String> fields = List.of("id", "questionText");

        when(questionService.getAllQuestionsWithFilterOutProperties(fields)).thenReturn(questions);

        mockMvc.perform(get("/questions/GetByFields")
                        .param("fields", "id", "questionText"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testQuestion.getId()))
                .andExpect(jsonPath("$[0].questionText").value(testQuestion.getQuestionText()))
                .andExpect(jsonPath("$[0].answerCount").doesNotExist());
    }
}



