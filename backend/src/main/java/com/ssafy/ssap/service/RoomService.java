package com.ssafy.ssap.service;

import com.ssafy.ssap.domain.studyroom.Participants;
import com.ssafy.ssap.domain.studyroom.ParticipantsRoleNs;
import com.ssafy.ssap.domain.studyroom.Room;
import com.ssafy.ssap.domain.studyroom.RoomLog;
import com.ssafy.ssap.dto.RoomDto;
import com.ssafy.ssap.repository.ParticipantsRepository;
import com.ssafy.ssap.repository.ParticipantsRoleNsRepository;
import com.ssafy.ssap.repository.RoomLogRepository;
import com.ssafy.ssap.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final ParticipantsRepository participantsRepository;
    private final RoomLogRepository roomLogRepository;

    /**
     * 스터디룸 생성
     */
    @Transactional
    public Long create(RoomDto roomDto) throws Exception {
        Room room = Room.builder()
                .title(roomDto.getTitle())
                .quota(roomDto.getQuota())
                .isPrivacy(roomDto.getIsPrivacy())
                .password(roomDto.getPassword())
                .endTime(LocalDateTime.now().plusHours(roomDto.getEndHour()).plusMinutes(roomDto.getEndMinute()))
                .imagePath(roomDto.getImagePath())
                .rule(roomDto.getRule())
                .build();
        roomRepository.save(room);

        // 참여자 추가 (방장)
        Participants participants = Participants.builder()
                .isOut(false)
                .role(new ParticipantsRoleNs("방장"))
                .room(room)
                .build();
        participantsRepository.save(participants);

        // 접속 기록 추가
        RoomLog roomLog = RoomLog.builder()
            .roomTitle(room.getTitle())
            .enterTime(LocalDateTime.now())
            .build();
        roomLogRepository.save(roomLog);

        return room.getId();
    }
}
