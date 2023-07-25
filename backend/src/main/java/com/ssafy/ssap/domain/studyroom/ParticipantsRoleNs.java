package com.ssafy.ssap.domain.studyroom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantsRoleNs {

    @Id
    @Column(length = 10)
    private String name;
}
