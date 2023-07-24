package com.ssafy.ssap.domain.studyroom;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "room_log")
@Getter
@Setter
public class RoomLog {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "room_title", length = 45)
    private String roomTitle;

    @Column(name = "enter_time")
    private LocalDateTime enterTime;

    @Column(name = "spend_hour")
    private LocalDateTime spendHour;
}
