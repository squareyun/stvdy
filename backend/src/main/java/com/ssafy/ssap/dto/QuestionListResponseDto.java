package com.ssafy.ssap.dto;

import com.ssafy.ssap.domain.qna.QuestionCategoryNs;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class QuestionListResponseDto {

    private Integer id;
    private String title;
    private String detail;
    private Integer hit;
    private LocalDateTime regist_time;
    private Boolean bestSelected;
    private QuestionCategoryNs category;
    private String userNickname;
    private Integer cntAnswer;
    private Integer questionScore;

}
