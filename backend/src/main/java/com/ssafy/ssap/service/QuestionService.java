package com.ssafy.ssap.service;

import com.ssafy.ssap.domain.qna.Question;
import com.ssafy.ssap.domain.qna.QuestionCategoryNs;
import com.ssafy.ssap.dto.QuestionCreateDto;
import com.ssafy.ssap.dto.QuestionListResponseDto;
import com.ssafy.ssap.repository.QueryRepository;
import com.ssafy.ssap.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QueryRepository queryRepository;

    /**
     * 질문 생성
     */
    @Transactional
    public Long create(QuestionCreateDto questionCreateDto) throws Exception {
        Question question = Question.builder()
                .title(questionCreateDto.getTitle())
                .detail(questionCreateDto.getContent())
                .registTime(LocalDateTime.now())
                .category(new QuestionCategoryNs(questionCreateDto.getCategory()))
                .build();

        questionRepository.save(question);

        return question.getId();
    }

    /**
     * 질문 수정
     */
    public void update(Long questionNo, QuestionCreateDto questionCreateDto) {
        Question question = Question.builder()
                .id(questionNo)
                .title(questionCreateDto.getTitle())
                .detail(questionCreateDto.getContent())
                .registTime(LocalDateTime.now())
                .category(new QuestionCategoryNs(questionCreateDto.getCategory()))
                .build();

        questionRepository.save(question);
    }

    /**
     * 질문 삭제
     * TODO: 연쇄적으로 연관된 FK 삭제되는지 확인 작업 필요
     */
    public void delete(Long questionNo) {
        questionRepository.deleteById(questionNo);
    }

    /**
     * 전체 목록 조회
     * 전체, 키워드 검색, 사용자 번호 검색을 지원
     */
    public List<QuestionListResponseDto> getList(String keyword, String nickname) {
//        List<Question> questionList = null;
//
//        if (StringUtils.hasText(keyword) && StringUtils.hasText(nickname)) {
//            System.out.println("1");
//        } else if (StringUtils.hasText(keyword)) {
//            System.out.println("2");
//            questionList = questionRepository.findByTitleContaining(keyword);
//        } else if (StringUtils.hasText(nickname)) {
//            // TODO
//        } else {
//            System.out.println("3");
//            questionList = questionRepository.findAll();
//        }


//        return questionList;
        return queryRepository.findAllQuestionWithKeywordAndNickName(keyword, nickname);
    }
}
