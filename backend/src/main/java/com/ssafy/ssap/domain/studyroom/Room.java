package com.ssafy.ssap.domain.studyroom;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    @NotNull
    private String title;

    @Column(columnDefinition = "TINYINT(8)")
    private int quota;

    @Column(name = "is_privacy", columnDefinition = "bit")
    @ColumnDefault("0")
    private Boolean isPrivacy;

    @Column(length = 45)
    private String password;

    //    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "end_time", columnDefinition = "timestamp")
    @NotNull
    private LocalDateTime endTime;

    @Column(name = "is_valid", columnDefinition = "bit")
    @ColumnDefault("1")
    private Boolean isValid;

    @Column(name = "session_id", length = 30)
    private String sessionId;

    @Column(name = "image_path", length = 45)
    private String imagePath;

    @Column
    private String rule;

    @Setter
    @Column(length = 3)
    private String code;

    @Builder.Default
    @OneToMany(mappedBy = "room")
    private List<Participant> participantList = new ArrayList<>();

}
