package com.ssafy.ssap.service;

import com.ssafy.ssap.domain.studyroom.Room;
import com.ssafy.ssap.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    /**
     * 스터디룸 생성
     */
    @Transactional
    public Long create(Room room) throws Exception {
        roomRepository.save(room);
        return room.getId();
    }
}
