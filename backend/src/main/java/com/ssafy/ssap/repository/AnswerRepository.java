package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.qna.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    @Query(value = "SELECT a, ai FROM Answer a LEFT JOIN a.articleImage ai JOIN a.question q WHERE a.question.id = ?1")
    List<Object[]> findByQuestionId(Integer questionId);
}
