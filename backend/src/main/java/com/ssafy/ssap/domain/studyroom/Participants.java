package com.ssafy.ssap.domain.studyroom;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class Participants {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "is_out")
    private boolean isOut;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "role_id")
    private ParticipantsRoleNs role;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}
