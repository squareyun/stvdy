package com.ssafy.ssap.service;

import com.ssafy.ssap.domain.qna.Question;
import com.ssafy.ssap.domain.qna.QuestionCategoryNs;
import com.ssafy.ssap.dto.QuestionCreateDto;
import com.ssafy.ssap.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

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

    public void delete(Long questionNo) {
        questionRepository.deleteById(questionNo);
    }
}
