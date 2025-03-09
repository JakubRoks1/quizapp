package com.example.quizapp.controller;

import com.example.quizapp.fixtures.QuizFixtures;
import com.example.quizapp.json.QuestionJson;
import com.example.quizapp.mappers.QuestionMapper;
import com.example.quizapp.mappers.QuestionMapperImpl;
import com.example.quizapp.model.Question;
import com.example.quizapp.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(QuestionController.class)
@Import({QuestionMapperImpl.class})  // Import the generated mapper implementation
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public QuestionMapper questionMapper() {
            return new QuestionMapperImpl();
        }
    }

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
        when(questionService.findById(testQuestion.getId())).thenReturn(testQuestion);

        mockMvc.perform(get("/questions/{id}", testQuestion.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testQuestion.getId()))
                .andExpect(jsonPath("$.questionText").value(testQuestion.getQuestionText()));
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
    void deleteQuestion_shouldReturnOkStatus() throws Exception {
        Question testQuestion = QuizFixtures.getQuiz().getQuestions().stream().findFirst().orElseThrow();
        doNothing().when(questionService).deleteQuestion(testQuestion.getId());

        mockMvc.perform(delete("/questions/{id}", testQuestion.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Question successfully deleted"));

        verify(questionService).deleteQuestion(testQuestion.getId());
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



