package com.example.quizapp.controller;

import com.example.quizapp.exception.QuizAppException;
import com.example.quizapp.json.FetchMode;
import com.example.quizapp.json.QuestionJson;
import com.example.quizapp.mappers.QuestionMapper;
import com.example.quizapp.model.Question;
import com.example.quizapp.service.QuestionService;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @Autowired
    public QuestionController(QuestionService questionService, QuestionMapper questionMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
    }

    @PostMapping
    public ResponseEntity<QuestionJson> createQuestion(@RequestBody QuestionJson questionJson) {
        Question question = questionMapper.mapToQuestion(questionJson);
        Question savedQuestion = questionService.addQuestion(question);
        QuestionJson responseJson = questionMapper.mapToQuestionJson(savedQuestion);
        return ResponseEntity.ok(responseJson);
    }

    @GetMapping
    public List<QuestionJson> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return questions.stream()
                .map(questionMapper::mapToQuestionJson)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionJson> getQuestionById(@PathVariable Long id) {
        return questionService.getQuestion(id)
                .map(questionMapper::mapToQuestionJson)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionJson> updateQuestion(@PathVariable Long id, @RequestBody QuestionJson questionJson) {
        try {
            Question updatedQuestion = questionMapper.mapToQuestion(questionJson);
            Question savedQuestion = questionService.updateQuestion(id, updatedQuestion);
            return ResponseEntity.ok(questionMapper.mapToQuestionJson(savedQuestion));
        } catch (QuizAppException e) {
            return ResponseEntity.status(e.getCode()).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteQuestion(@PathVariable Long id) {
        return questionService.getQuestion(id)
                .map(question -> {
                    questionService.deleteQuestion(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/GetByFields")
    @Transactional
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<List<QuestionJson>> getSpecified(@RequestParam List<String> fields) {
        List<Question> questions = questionService.getAllQuestionsWithFilteredProperties(fields);
        List<QuestionJson> questionJsons = questions.stream()
                .map(questionMapper::mapToQuestionJson)
                .toList();
        return ResponseEntity.ok(questionJsons);
    }

    @Transactional
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @GetMapping("/GetByFilteredFields")
    public ResponseEntity<List<QuestionJson>> getSpecifiedFiltered(@RequestParam List<String> fields) {
        List<Question> questions = questionService.getAllQuestionsWithFilterOutProperties(fields);

        List<QuestionJson> questionFilteredJsons = questions.stream()
                .map(questionMapper::mapToQuestionJson)
                .toList();

        return ResponseEntity.ok(questionFilteredJsons);
    }
}