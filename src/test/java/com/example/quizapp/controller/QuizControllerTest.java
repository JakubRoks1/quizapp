package com.example.quizapp.controller;

import com.example.quizapp.fixtures.QuizFixtures;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

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
        when(quizService.getQuiz(Mockito.eq(1L), Mockito.any(Boolean.class))).thenReturn(Optional.of(QuizFixtures.getQuiz()));


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

}