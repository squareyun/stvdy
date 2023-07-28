package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.qna.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByTitleContaining(String title);
}
