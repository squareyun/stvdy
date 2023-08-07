package com.ssafy.ssap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class RoomDto {
    public RoomDto(int id, String title, int quota, Boolean isPrivacy, Boolean isValid, String sessionId, String password, LocalDateTime due_time, String imagePath, String rule){
        this.id = id;
        this.title = title;
        this.quota = quota;
        this.isPrivacy = isPrivacy;
        this.isValid = isValid;
        this.sessionId = sessionId;
        this.password = password;
        this.due_time = due_time;
        this.imagePath = imagePath;
        this.rule = rule;
    }
    private int id;
    private String title;
    private int quota;
    private Boolean isPrivacy;
    private Boolean isValid;
    private String sessionId;
    private String password;
    private LocalDateTime due_time;
    private String imagePath;
    private String rule;
    private Integer currentNumber;
}
