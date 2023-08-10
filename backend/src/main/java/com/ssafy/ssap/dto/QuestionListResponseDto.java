package com.ssafy.ssap.dto;

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
    private String category;
    private String userNickname;
    private Integer cntAnswer;
    private Integer questionScore;
    private Integer userNo;

    public int getQuestionScore() {
        return questionScore == null ? 0 : questionScore;
    }
}
