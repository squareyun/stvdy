package com.ssafy.ssap.domain.studyroom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20)
    private String title;

    @Column(columnDefinition = "TINYINT(8)")
    private int quota;

    @Column(name = "is_privacy", columnDefinition = "TINYINT(1)")
    private Boolean isPrivacy;

    @Column(length = 45)
    private String password;

    //    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "end_time", columnDefinition = "timestamp")
    private LocalDateTime endTime;

    @Column(name = "image_path", length = 45)
    private String imagePath;

    private String rule;

}
