package com.example.quizapp.controller;

import com.example.quizapp.entity.QuestionEntity;
import com.example.quizapp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping
    public QuestionEntity createQuestion(@RequestBody QuestionEntity question) {
        return questionRepository.save(question);
    }

    @GetMapping
    public List<QuestionEntity> getAllQuestions(@RequestHeader Map<String, String> headerAbc) {
        System.out.println(headerAbc);
        return questionRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<QuestionEntity> getQuestionById(@PathVariable Long id) {
        Optional<QuestionEntity> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return ResponseEntity.ok(question.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<QuestionEntity> updateQuestion(@PathVariable Long id, @RequestBody QuestionEntity questionDetails) {
//        return questionRepository.findById(id)
//                .map(question -> {
//                    question.setQuestionText(questionDetails.getQuestionText());
//                    question.setAnswer(questionDetails.getAnswer());
//                    QuestionEntity updatedQuestion = questionRepository.save(question);
//                    return ResponseEntity.ok().body(updatedQuestion);
//                }).orElse(ResponseEntity.notFound().build());
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteQuestion(@PathVariable Long id) {
        return questionRepository.findById(id)
                .map(question -> {
                    questionRepository.delete(question);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}