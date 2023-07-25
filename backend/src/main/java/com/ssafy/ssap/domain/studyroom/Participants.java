package com.ssafy.ssap.domain.studyroom;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Participants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_out", columnDefinition = "TINYINT(1)")
    @ColumnDefault("false")
    private Boolean isOut;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "role")
    private ParticipantsRoleNs role;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}
