package com.ssafy.ssap.service;

import com.ssafy.ssap.domain.studyroom.Room;
import com.ssafy.ssap.dto.RoomDto;
import com.ssafy.ssap.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    /**
     * 스터디룸 생성
     */
    @Transactional
    public Long create(RoomDto roomDto) throws Exception {
        Room room = Room.builder()
                .title(roomDto.getTitle())
                .quota(roomDto.getQuota())
                .isPrivacy(roomDto.isPrivacy())
                .password(roomDto.getPassword())
                .endTime(LocalDateTime.now().plusHours(roomDto.getEndHour()).plusMinutes(roomDto.getEndMinute()))
                .imagePath(roomDto.getImagePath())
                .rule(roomDto.getRule())
                .build();
        roomRepository.save(room);
        System.out.println(room);
        return room.getId();
    }
}
