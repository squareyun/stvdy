package com.ssafy.ssap.dto;

import lombok.Getter;

@Getter
public class QuestionCreateDto {

    private long userNo;
    private String title;
    private String content;
    private String category;

}
