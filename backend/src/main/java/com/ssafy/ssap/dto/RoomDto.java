package com.ssafy.ssap.dto;

import com.ssafy.ssap.domain.studyroom.Room;
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
    private Integer currentNumber;

    public RoomDto(int id, String title, int quota, Boolean isPrivacy, Boolean isValid, String sessionId, String password, LocalDateTime due_time, String rule){
        this.id = id;
        this.title = title;
        this.quota = quota;
        this.isPrivacy = isPrivacy;
        this.isValid = isValid;
        this.sessionId = sessionId;
        this.password = password;
        this.due_time = due_time;
        this.imagePath = null;
        this.rule = rule;
    }

    public RoomDto(Room room, String imagePath) {
        this.id = room.getId();
        this.title = room.getTitle();
        this.quota = room.getQuota();
        this.isPrivacy = room.getIsPrivacy();
        this.isValid = room.getIsValid();
        this.sessionId = room.getSessionId();
        this.password = room.getPassword();
        this.due_time = room.getEndTime();
//        this.imagePath = room.getImagePath();
        this.rule = room.getRule();

        this.imagePath = imagePath;
    }
}
