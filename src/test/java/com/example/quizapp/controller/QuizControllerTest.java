package com.example.quizapp.controller;

import com.example.quizapp.fixtures.QuizFixtures;
import com.example.quizapp.mappers.QuizMapper;
import com.example.quizapp.mappers.QuizMapperImpl;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.service.QuizService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.val;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * x1) W przynajmniej jednym teście Json request jest czytany z pliku (ten co wysylasz), i expected json też z pliku - masz dwa pliki, jeden to givenJson drugi expectedJson (x) createQuiz_shouldReturnCreatedQuiz
 * x1*) Zaczytać template Jsona i podmienić wartość (tj. w teście templateTest) x getById()
 * x2) Pozamieniaj testy na użycie JSONów (całość)
 * x3) Testy negatywne - np. dla create gdzie podajesz ID - parametrized test - dynamiczne doklejenie wartości do jsona i sprawdzenie createQuiz_withProvidedId_shouldReturnBadRequest
 * 4) test na getQuiz który ma 2 pytania (FULL) wariant z assercją JsonPath i JSON
 */
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
        val expectedJsonTemplate = """
        {
          "id": ${id},
          "quizCategory": "${quizCategory}",
          "description": "${description}"
        }""";

        val concreteExpectedJson = expectedJsonTemplate
                .replace("${id}", "1")
                .replace("${quizCategory}", "Astronomia")
                .replace("${description}", "Pytania o planetach i księżycach");

        when(quizService.getQuiz(eq(1L), any(Boolean.class))).thenReturn(Optional.of(QuizFixtures.getQuiz()));

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/quizzes/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(concreteExpectedJson))
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        System.out.println("Response: " + contentAsString);

        Quiz quiz = objectMapper.readValue(contentAsString, Quiz.class);
        System.out.println("Quiz: " + quiz);

    }

    @Test
    public void templateTest() {
        val givenJsonTemplate = """
            {
              "quizCategory" : ${quizCategory},
              "description" : "Pytania o planetach i księżycach"
            }""";

        System.out.println(givenJsonTemplate);

        val concreteJson = givenJsonTemplate.replace("${quizCategory}", "\"Piłka nożna\"");

        System.out.println(concreteJson);
    }

    @ParameterizedTest
    @ValueSource(strings = {"field1", "field2"})
    public void paramsTest(String newField) {
        val givenJsonTemplate = """
            {
              "quizCategory" : "Astronomia",
            }""";

        System.out.println(givenJsonTemplate);

        // do poprawy logika
        StringBuilder sb = new StringBuilder(givenJsonTemplate);
        sb.append(", ").append(newField).append(": \"testValue\"");

        System.out.println(sb);
    }

    @Test
    public void createQuiz_shouldReturnCreatedQuiz() throws Exception {
        String givenJson = new String(Files.readAllBytes(Paths.get("jsons/separate/givenJson.json")));
        String expectedJson = new String(Files.readAllBytes(Paths.get("jsons/separate/expectedJson.json")));

        when(quizService.addQuiz(any(Quiz.class))).thenReturn(QuizFixtures.getQuiz());

        mockMvc.perform(MockMvcRequestBuilders.post("/quizzes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenJson))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("$.id").value("1"),
                        MockMvcResultMatchers.content().json(expectedJson, JsonCompareMode.STRICT)
                );
    }

    @Test
    void getAllQuizzes_countMode_shouldReturnQuizzesWithQuestionCount() throws Exception {
        List<Quiz> quizzes = List.of(QuizFixtures.getQuiz());
        when(quizService.getAllQuizzes()).thenReturn(quizzes);

        String expectedJson = """
    [{
      "value": {
        "id": 1,
        "questionsCount": 1,
        "quizCategory": "Astronomia",
        "description": "Pytania o planetach i księżycach"
      }
    }]
    """;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/quizzes")
                        .param("mode", "COUNT"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        JSONAssert.assertEquals(expectedJson, result.getResponse().getContentAsString(), false);
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

        String expectedJson = """
    [{
      "id": 1,
      "quizCategory": "Astronomia",
      "description": "Pytania o planetach i księżycach"
    }]
    """;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/quizzes/GetByFields")
                        .param("fields", "id", "quizCategory"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        JSONAssert.assertEquals(expectedJson, result.getResponse().getContentAsString(), false);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "100", "999"})
    void createQuiz_withProvidedId_shouldReturnBadRequest(String id) throws Exception {
        String baseJson = new String(Files.readAllBytes(Paths.get("jsons/separate/givenJson.json")));
        JSONObject jsonObject = new JSONObject(baseJson);
        jsonObject.put("id", id);
        String modifiedJson = jsonObject.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/quizzes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modifiedJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("ID should not be provided for quiz creation"));
    }


}