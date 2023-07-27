package com.ssafy.ssap.repository;

import com.querydsl.core.BooleanBuilder;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class QueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public QueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<QuestionListResponseDto> findAllQuestionWithKeywordAndNickName(String keyword, String nickname, Pageable pageable) {
        QQuestion question = QQuestion.question;
        QAnswer answer = QAnswer.answer;
        QLikes likes = QLikes.likes;

        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(keyword)) {
            builder.and(question.title.containsIgnoreCase(keyword));
        }

        //TODO: nickname 동적 쿼리 작성

        System.out.println(pageable.getOffset());
        System.out.println(pageable.getPageSize());

        JPAQuery<QuestionListResponseDto> query = jpaQueryFactory
            .select(Projections.constructor(QuestionListResponseDto.class,
                question.id, question.title, question.detail, question.registTime,
                question.category, question.answerList.size().as("cntAnswer"),
                JPAExpressions.select(likes.isGood.when(true).then(1).otherwise(0).sum().castToNum(Integer.class))
                    .from(likes)
                    .where(likes.question.id.eq(question.id))))
            .from(question)
            .leftJoin(question.likes, likes)
            .leftJoin(question.answer, answer)
            .where(builder)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(question.registTime.desc());

        List<QuestionListResponseDto> content = query.fetch();

        Long totalCount = jpaQueryFactory
            .select(question.count())
            .from(question)
            .where(builder)
            .fetchOne();

        return new PageImpl<>(content, pageable, totalCount);
    }
}