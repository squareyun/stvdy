package com.ssafy.ssap.domain.studyroom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20)
    private String title;

    //    @Column(columnDefinition = "TINYINT(8)")
    private int quota;

    @Column(name = "is_privacy")
    private boolean isPrivacy;

    @Column(length = 45)
    private String password;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "image_path", length = 45)
    private String imagePath;

    private String rule;
}
