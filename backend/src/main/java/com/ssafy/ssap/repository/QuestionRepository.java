package com.ssafy.ssap.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
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

	@Query("SELECT q.category FROM Question q GROUP BY q.category ORDER BY COUNT(q.category) DESC")
	List<String> findCategoryRanking(Pageable pageable);
}
