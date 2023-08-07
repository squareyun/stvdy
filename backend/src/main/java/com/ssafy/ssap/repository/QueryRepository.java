package com.ssafy.ssap.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ssap.domain.qna.QAnswer;
import com.ssafy.ssap.domain.qna.QLikes;
import com.ssafy.ssap.domain.qna.QQuestion;
import com.ssafy.ssap.domain.user.QUser;
import com.ssafy.ssap.dto.QuestionDetailResponseDto;
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
        QUser user = QUser.user;
        QAnswer answer = QAnswer.answer;
        QLikes likes = QLikes.likes;

        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(keyword)) {
            builder.and(question.title.containsIgnoreCase(keyword));
        }

        if (StringUtils.hasText(nickname)) {
            builder.and(question.user.nickname.containsIgnoreCase(nickname));
        }

        JPAQuery<QuestionListResponseDto> query = jpaQueryFactory
                .select(Projections.constructor(QuestionListResponseDto.class,
                        question.id,
                        question.title,
                        question.detail,
                        question.hit,
                        question.registTime,
                        question.answer.isNotNull(),
                        question.category,
                        question.user.nickname,
                        question.answerList.size().as("cntAnswer"),
                        JPAExpressions.select(likes.isGood.when(true).then(1).otherwise(0).sum().castToNum(Integer.class))
                                .from(likes)
                                .where(likes.question.id.eq(question.id))))
                .from(question)
                .leftJoin(question.likes, likes)
                .leftJoin(question.answer, answer)
                .leftJoin(question.user, user)
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

    public QuestionDetailResponseDto findQuestionById(Integer questionNo) {
        QQuestion question = QQuestion.question;
        QLikes likes = QLikes.likes;
        QUser user = QUser.user;

        return jpaQueryFactory.select(Projections.constructor(QuestionDetailResponseDto.class,
                        question.id,
                        question.title,
                        question.detail,
                        question.hit,
                        question.registTime,
                        question.answer.isNotNull(),
                        question.category,
                        question.user.nickname,
                        JPAExpressions.select(likes.isGood.when(true).then(1).otherwise(0).sum().castToNum(Integer.class))
                                .from(likes)
                                .where(likes.question.id.eq(question.id))))
                .from(question)
                .leftJoin(question.user, user)
                .leftJoin(question.likes, likes)
                .where(question.id.eq(questionNo))
                .fetchOne();
    }

    public Boolean findLikesIsLikedQuestion(Integer userNo, Integer questionNo) {
        if (userNo == null) {
            return null;
        }

        QLikes likes = QLikes.likes;

        return jpaQueryFactory.select(likes.isGood)
                .from(likes)
                .where(likes.question.id.eq(questionNo).and(likes.user.id.eq(userNo)))
                .fetchOne();

    }
}