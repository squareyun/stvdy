package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.qna.Answer;
import com.ssafy.ssap.dto.AnswerResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    @Query(value = "SELECT new com.ssafy.ssap.dto.AnswerResponseDto(a.id, a.detail, a.registTime, a.answerScore, case when q.answer = a then true else false end, a.user.nickname, a.user.id) FROM Answer a JOIN a.question q WHERE a.question.id = ?1 ")
    List<AnswerResponseDto> findByQuestionId(Integer questionId);
}
