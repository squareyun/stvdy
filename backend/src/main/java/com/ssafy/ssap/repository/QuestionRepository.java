package com.ssafy.ssap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.ssap.domain.qna.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	@Modifying
	@Query("update Question q set q.hit = q.hit + 1 where q.id = ?1")
	void updateHits(Integer questionNo);
}
