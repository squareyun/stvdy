package com.ssafy.ssap.domain.studyroom;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "room_log")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomLog {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "room_title", length = 45)
    private String roomTitle;

    @Column(name = "enter_time", columnDefinition = "timestamp")
    private LocalDateTime enterTime;

    @Column(name = "spend_hour", columnDefinition = "time")
    private LocalDateTime spendHour;
}
