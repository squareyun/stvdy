package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.qna.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}