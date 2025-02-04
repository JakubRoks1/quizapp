package com.example.quizapp.controller;

import com.example.quizapp.fixtures.QuizFixtures;
import com.example.quizapp.json.QuizJson;
import com.example.quizapp.mappers.QuizMapper;
import com.example.quizapp.mappers.QuizMapperImpl;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.service.QuizService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;


import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = QuizController.class)
@Import(QuizMapperImpl.class)
class QuizControllerTest {

    @MockitoBean
    private QuizService quizService;

    @Autowired
//    @MockitoBean
    private QuizMapper quizMapper;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void delete() throws Exception {
        mockMvc
            .perform(MockMvcRequestBuilders.delete("/quizzes/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());


        System.out.println("test");
    }

    @Test
    public void getById() throws Exception {
        when(quizService.getQuiz(eq(1L), any(Boolean.class))).thenReturn(Optional.of(QuizFixtures.getQuiz()));


        var contentAsString = mockMvc
            .perform(MockMvcRequestBuilders.get("/quizzes/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.quizCategory").value("Astronomia"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Pytania o planetach i księżycach"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.questions").doesNotExist())
            .andReturn().getResponse().getContentAsString();

        System.out.println(contentAsString);

        var quiz = objectMapper.readValue(contentAsString, Quiz.class);
        System.out.println(quiz);

    }

    @Test
    public void createQuiz_shouldReturnCreatedQuiz() throws Exception {
        QuizJson quizJson = new QuizJson();
        quizJson.setQuizCategory("Astronomia");
        quizJson.setDescription("Pytania o planetach i księżycach");

        Quiz savedQuiz = QuizFixtures.getQuiz();

        when(quizService.addQuiz(any(Quiz.class))).thenReturn(savedQuiz);

        mockMvc.perform(MockMvcRequestBuilders.post("/quizzes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quizJson)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    void getAllQuizzes_countMode_shouldReturnQuizzesWithQuestionCount() throws Exception {
        List<Quiz> quizzes = List.of(QuizFixtures.getQuiz());
        when(quizService.getAllQuizzes()).thenReturn(quizzes);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/quizzes")
                        .param("mode", "COUNT"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].value.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].value.questionsCount").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].value.quizCategory").value("Astronomia"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].value.description").value("Pytania o planetach i księżycach"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void getQuizById_notFound_shouldReturnNotFound() throws Exception {
        when(quizService.getQuiz(eq(999L), any(Boolean.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/quizzes/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteQuiz_shouldReturnOkStatus() throws Exception {
        when(quizService.deleteQuiz(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/quizzes/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Quiz successfully deleted"));
    }

    @Test
    void getSpecified_shouldReturnFilteredQuizzes() throws Exception {
        List<Quiz> quizzes = List.of(QuizFixtures.getQuiz());
        List<String> fields = List.of("id", "quizCategory");

        when(quizService.getAllQuizzesWithFilterOutProperties(fields)).thenReturn(quizzes);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/quizzes/GetByFields")
                        .param("fields", "id", "quizCategory"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quizCategory").value("Astronomia"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").doesNotExist())
                .andReturn();

        System.out.println("Response Body: " + result.getResponse().getContentAsString());
    }
}