package com.ssafy.ssap.dto;

import lombok.Getter;

@Getter
public class QuestionCreateDto {

    private int userNo;
    private String title;
    private String content;
    private String category;

}
