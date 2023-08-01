package com.ssafy.ssap.service;

import com.ssafy.ssap.domain.studyroom.Participants;
import com.ssafy.ssap.domain.studyroom.Room;
import com.ssafy.ssap.domain.studyroom.RoomLog;
import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.dto.RoomCreateDto;
import com.ssafy.ssap.repository.*;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final ParticipantsRepository participantsRepository;
    private final ParticipantsRoleNsRepository participantsRoleNsRepository;
    private final RoomLogRepository roomLogRepository;
    private final UserRepository userRepository;

    /**
     * OpenVidu variables
     */
    private OpenVidu openVidu;
    private final String OPENVIDU_URL = "http://localhost:4443/";
    private final String SECRET = "MY_SECRET";

    /**
     * 스터디룸 생성
     */
    @Transactional
    public Integer create(RoomCreateDto roomCreateDto) {
        // 방 추가
        Room room = Room.builder()
                .title(roomCreateDto.getTitle())
                .quota(roomCreateDto.getQuota())
                .isPrivacy(roomCreateDto.getIsPrivacy())
                .isValid(true)
                .password(roomCreateDto.getPassword())
                .endTime(LocalDateTime.now().plusHours(roomCreateDto.getEndHour()).plusMinutes(roomCreateDto.getEndMinute()))
                .imagePath(roomCreateDto.getImagePath())
                .rule(roomCreateDto.getRule())
                .build();
        roomRepository.save(room);

        addParticipant(room, "호스트");
        addRoomLog(room,roomCreateDto.getUserNo());

        return room.getId();
    }

    public void addParticipant(Room room, String role){
        // 참여자 추가 (방장)
        Participants participants = Participants.builder()
                .isOut(false)
                .role(participantsRoleNsRepository.findByName(role))
                .room(room)
                .build();
        participantsRepository.save(participants);
    }

    public void addRoomLog(Room room, Integer userId){
        User user = userRepository.findById((long)userId).get();
        // 접속 기록 추가
        RoomLog roomLog = RoomLog.builder()
                .roomTitle(room.getTitle())
                .enterTime(LocalDateTime.now())
                .room(room)
                .user(user)
                .build();
        roomLogRepository.save(roomLog);
    }

    /**
     * 스터디룸 폐쇄
     */
    @Transactional

    public void close(Integer roomNo) {
        // 방에 접속한 사람들의 room_log 데이터 업데이트
        roomLogRepository.updateSpendHourByAllRoomId(roomNo);
        participantsRepository.deleteByRoomId(roomNo);

        roomRepository.setValidToZeroByRoomId(roomNo);
    }

    public String makeSession() {
        //session 생성, connection 생성, 토큰 생성
        Session session;
        String token;
        try {
            openVidu = new OpenVidu(OPENVIDU_URL,SECRET);
            session = this.openVidu.createSession();
            token = joinSession(session.getSessionId()); //joinSession에서 connection 생성
            return token;
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            throw new RuntimeException(e);
        }
    }

    public String joinSession(String sessionId) {
        String token;

        //입장 요청한 session이 존재하는지 확인 (무조건 존재해야함!)
        Session session = openVidu.getActiveSession(sessionId);
        if (session == null) {
            System.out.println("No kidding me");
            return null;
        }

        //커넥션 생성 및 토큰 반환
        try {
            ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).build();
            token = session.createConnection(connectionProperties).getToken();
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            throw new RuntimeException(e);
        }

        return token;
    }

    public Integer findRoomId(Integer roomcode) {
        return 0;
    }
}
