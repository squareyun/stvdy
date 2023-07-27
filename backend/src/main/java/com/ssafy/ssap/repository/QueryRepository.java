package com.ssafy.ssap.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
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
                    JPAExpressions.select(likes.isGood.when(true).then(1).otherwise(0).sum().castToNum(Integer.class))
                        .from(likes)
                        .where(likes.question.id.eq(question.id))))
                .from(question)
                .leftJoin(question.likes, likes)
                .leftJoin(question.answer, answer);

        return query.fetch();
    }
}