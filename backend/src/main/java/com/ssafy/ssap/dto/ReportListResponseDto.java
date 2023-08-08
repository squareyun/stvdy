package com.ssafy.ssap.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReportListResponseDto {

    private Integer id;
    private String detail;
    private Integer reporterId;
    private String reporterNickName;
    private Integer targetId;
    private String targetNickName;
    private String category;
    private LocalDateTime registTime;

    public ReportListResponseDto(Integer id,
                                 String detail,
                                 Integer reporterId,
                                 String reporterNickName,
                                 Integer targetId,
                                 String targetNickName,
                                 String category,
                                 LocalDateTime registTime) {
        this.id = id;
        this.detail = detail;
        this.reporterId = reporterId;
        this.reporterNickName = reporterNickName;
        this.targetId = targetId;
        this.targetNickName = targetNickName;
        this.category = category;
        this.registTime = registTime;
    }
}
