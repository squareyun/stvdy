package com.ssafy.ssap.util;

import com.ssafy.ssap.domain.studyroom.Participant;
import com.ssafy.ssap.repository.ParticipantRepository;
import com.ssafy.ssap.repository.ParticipantRoleNsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomUtil {
    private final ParticipantRepository participantRepository;
    private final ParticipantRoleNsRepository participantRoleNsRepository;
    public boolean isStaff(Integer roomNo, Integer staffNo) {
        Participant participant = participantRepository.findByRoomIdAndUserId(roomNo, staffNo).orElse(null);

        try{
            return participant.getRole().equals(participantRoleNsRepository.findByName("staff"))
                    || participant.getRole().equals(participantRoleNsRepository.findByName("host"));
        } catch(NullPointerException e){
            throw e;
        }
    }
}
