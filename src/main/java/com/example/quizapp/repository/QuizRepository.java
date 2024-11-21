package com.example.quizapp.repository;

import com.example.quizapp.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<QuizEntity, Long> {

    @Query("SELECT q FROM QuizEntity q LEFT JOIN FETCH q.questions WHERE q.id = :id")
    Optional<QuizEntity> findByIdWithQuestions(@Param("id") Long id);

    default Optional<QuizEntity> findByIdWithoutQuestions(Long id) {
        var byId = findById(id);
        byId.ifPresent(entity -> entity.setQuestions(null));
        return byId;
    }

    @Query("SELECT SIZE(q.questions) FROM QuizEntity q WHERE q.id = :id")
    Integer findQuestionCountById(Long id);

//    @Transactional
//    default Optional<QuizEntity> findByIdWithQuestions(Long id) {
//        var byId = findById(id);
//        byId.ifPresent(QuizEntity::getQuestions);
//        return byId;
//    }
}
