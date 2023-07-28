package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.qna.QuestionCategoryNs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionCategoryNsRepository extends JpaRepository<QuestionCategoryNs, Integer> {
}
