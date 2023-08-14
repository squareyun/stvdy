package com.ssafy.ssap.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ssap.domain.qna.*;
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
public class QuestionQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public QuestionQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<QuestionListResponseDto> findAllQuestionWithKeywordAndNickName(String keyword, String nickname, Boolean noAnsFilter, Boolean noBestAnsFilter, Pageable pageable) {
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

        if (Boolean.TRUE.equals(noAnsFilter)) {
            builder.and(question.answerList.isEmpty());
        }

        if (Boolean.TRUE.equals(noBestAnsFilter)) {
            builder.and(question.answer.isNull());
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
                        JPAExpressions.select(likes.isGood.when(true).then(1).when(false).then(-1).otherwise(0).sum())
                                .from(likes)
                                .where(likes.question.id.eq(question.id)),
                        question.user.id))
                .from(question)
                .leftJoin(question.likes, likes)
                .leftJoin(question.answerList, answer)
                .leftJoin(question.user, user)
                .where(builder)
                .groupBy(question.id)
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
        QArticleImage articleImage = QArticleImage.articleImage;

        // Fetch the main question with its likes count first
        Question questionRes = jpaQueryFactory
                .select(question)
                .from(question)
                .leftJoin(question.user, user)
                .where(question.id.eq(questionNo))
                .fetchOne();

        Integer questionScore = jpaQueryFactory.select(likes.isGood.when(true).then(1).otherwise(-1).sum().coalesce(0))
                .from(likes)
                .where(likes.question.id.eq(questionRes.getId()))
                .fetchOne();

        // Fetch all articleImages for the question
        List<String> relatedImagePaths = jpaQueryFactory
                .select(articleImage.imagePath)
                .from(articleImage)
                .where(articleImage.question.id.eq(questionNo))
                .fetch();

        assert questionRes != null;

        QuestionDetailResponseDto detailResponseDto = new QuestionDetailResponseDto(
                questionRes.getId(),
                questionRes.getTitle(),
                questionRes.getDetail(),
                questionRes.getHit(),
                questionRes.getRegistTime(),
                (questionRes.getAnswer() != null),
                questionRes.getCategory(),
                questionRes.getUser().getNickname(),
                questionScore,
                questionRes.getUser().getId(),
                questionRes.getUser().getProfileImagePath(),
                relatedImagePaths
        );

        return detailResponseDto;
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

    public Page<QuestionListResponseDto> findAllByUserId(Integer userNo, Pageable pageable) {

        QQuestion question = QQuestion.question;
        QUser user = QUser.user;
        QAnswer answer = QAnswer.answer;
        QLikes likes = QLikes.likes;

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
                        JPAExpressions.select(likes.isGood.when(true).then(1).when(false).then(-1).otherwise(0).sum())
                                .from(likes)
                                .where(likes.question.id.eq(question.id)),
                        question.user.id))
                .from(question)
                .leftJoin(question.likes, likes)
                .leftJoin(question.answerList, answer)
                .leftJoin(question.user, user)
                .where(question.user.id.eq(userNo))
                .groupBy(question.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(question.registTime.desc());

        List<QuestionListResponseDto> content = query.fetch();

        Long totalCount = jpaQueryFactory
                .select(question.count())
                .from(question)
                .where(question.id.eq(userNo))
                .fetchOne();

        return new PageImpl<>(content, pageable, totalCount);
    }
}
