package com.ssafy.ssap.domain.studyroom;

import com.ssafy.ssap.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "room_log")
public class RoomLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "room_title", length = 45)
    private String roomTitle;

    @Column(name = "enter_time", columnDefinition = "timestamp")
    private LocalDateTime enterTime;

    @Setter
    @Column(name = "spend_hour")
    private LocalTime spendHour;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
