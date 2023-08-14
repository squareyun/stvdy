package com.ssafy.ssap.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class QuestionDetailResponseDto {

    private Integer id;
    private String title;
    private String detail;
    private Integer hit;
    private LocalDateTime regist_time;
    private Boolean bestSelected;
    private String category;
    private String userNickname;
    private Integer questionScore;
    private Integer userNo;
    private String profileImage;
    private List<String> detailImages;

    public QuestionDetailResponseDto(Integer id, String title, String detail, Integer hit, LocalDateTime regist_time, Boolean bestSelected, String category, String userNickname, Integer questionScore, Integer userNo, String profileImage, List<String> detailImages) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.hit = hit;
        this.regist_time = regist_time;
        this.bestSelected = bestSelected;
        this.category = category;
        this.userNickname = userNickname;
        this.questionScore = (questionScore != null) ? questionScore : 0;
        this.userNo = userNo;
        this.profileImage = profileImage;
        this.detailImages = detailImages;
    }
}
