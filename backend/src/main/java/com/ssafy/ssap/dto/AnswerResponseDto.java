package com.ssafy.ssap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class AnswerResponseDto {

    private Integer id;
    private String detail;
    private LocalDateTime regist_time;
    private Integer answerScore;
    private Boolean bestSelected;
    private String userNickname;
    private Integer userNo;

}
