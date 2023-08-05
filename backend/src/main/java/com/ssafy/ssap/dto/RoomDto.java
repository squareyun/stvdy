package com.ssafy.ssap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class RoomDto {
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
}
