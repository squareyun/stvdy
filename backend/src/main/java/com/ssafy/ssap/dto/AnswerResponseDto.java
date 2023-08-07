package com.ssafy.ssap.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AnswerResponseDto {

    private Integer id;
    private String detail;
    private LocalDateTime regist_time;
    private Integer answerScore;
    private Boolean bestSelected;

}
