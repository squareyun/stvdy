package com.ssafy.ssap.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ssap.domain.qna.QAnswer;
import com.ssafy.ssap.domain.qna.QLikes;
import com.ssafy.ssap.domain.qna.QQuestion;
import com.ssafy.ssap.dto.QuestionListResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public QueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<QuestionListResponseDto> findAllQuestionWithKeywordAndNickName(String keyword, String nickname) {
        QQuestion question = QQuestion.question;
        QAnswer answer = QAnswer.answer;
        QLikes likes = QLikes.likes;

        JPAQuery<QuestionListResponseDto> query = jpaQueryFactory
                .select(Projections.constructor(QuestionListResponseDto.class,
                        question.id, question.title, question.detail, question.registTime,
                        question.category, question.answerList.size().as("cntAnswer"),
                        JPAExpressions.select(likes.count()) // 여기서 likes 테이블의 결과 개수를 추가합니다.
                                .from(likes)
                                .where(likes.question.id.eq(question.id))))
                .from(question)
                .leftJoin(question.likes, likes)
                .leftJoin(question.answer, answer);

        return query.fetch();
    }
}