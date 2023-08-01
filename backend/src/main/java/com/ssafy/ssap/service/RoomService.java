package com.ssafy.ssap.service;

import com.ssafy.ssap.domain.studyroom.Participants;
import com.ssafy.ssap.domain.studyroom.ParticipantsRoleNs;
import com.ssafy.ssap.domain.studyroom.Room;
import com.ssafy.ssap.domain.studyroom.RoomLog;
import com.ssafy.ssap.dto.RoomCreateDto;
import com.ssafy.ssap.repository.ParticipantsRepository;
import com.ssafy.ssap.repository.ParticipantsRoleNsRepository;
import com.ssafy.ssap.repository.RoomLogRepository;
import com.ssafy.ssap.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import com.ssafy.ssap.repository.RoomLogRepository;
import com.ssafy.ssap.repository.RoomRepository;
import io.openvidu.java.client.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final ParticipantsRepository participantsRepository;
    private final ParticipantsRoleNsRepository participantsRoleNsRepository;
    private final RoomLogRepository roomLogRepository;

    /**
     * OpenVidu variables
     */
    private OpenVidu openVidu;
    private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
    private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();
    private String OPENVIDU_URL;
    private String SECRET;

    /**
     * 스터디룸 생성
     */
    @Transactional
    public Long create(RoomCreateDto roomCreateDto) throws Exception {
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

        // 참여자 추가 (방장)
        Participants participants = Participants.builder()
                .isOut(false)
                .role(participantsRoleNsRepository.findByName("호스트"))
                .room(room)
                .build();
        participantsRepository.save(participants);

        // 접속 기록 추가
        RoomLog roomLog = RoomLog.builder()
                .roomTitle(room.getTitle())
                .enterTime(LocalDateTime.now())
                .room(room)
                .build();
        roomLogRepository.save(roomLog);

        return room.getId();
    }

    /**
     * 스터디룸 폐쇄
     */
    @Transactional

    public void close(Long roomNo) {
        // 방에 접속한 사람들의 room_log 데이터 업데이트
        roomLogRepository.updateSpendHourByAllRoomId(roomNo);
        participantsRepository.deleteByRoomId(roomNo);

        roomRepository.setValidToZeroByRoomId(roomNo);
    }

    public String makeSession(RoomCreateDto roomCreateDto) throws ParseException, OpenViduJavaClientException, OpenViduHttpException {
        System.out.println("makeSession 진입");
//        JSONObject sessionJSON = (JSONObject) new JSONParser().parse(roomCreateDto.getTitle());
        String sessionName = roomCreateDto.getTitle();
        OpenViduRole role = OpenViduRole.MODERATOR;
//        String serverData = "{\"serverData\": \""+roomCreateDto.getUserNo()+"\"}";
//        System.out.println("serverData = "+serverData);
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)
//                .data(serverData)
                .role(role)
                .build();

        JSONObject responseJson = new JSONObject();

        Session session = null;
        try {
            //openvidu-roles-java에선 생성자에서 처리하는 부분
            openVidu = new OpenVidu("http://localhost:4443/","MY_SECRET");
            session = this.openVidu.createSession();
            String token = session.createConnection(connectionProperties).getToken();
            System.out.println("session is "+session);
            System.out.println("token is "+token);

            this.mapSessions.put(sessionName, session);
            this.mapSessionNamesTokens.put(sessionName, new ConcurrentHashMap<>());
            this.mapSessionNamesTokens.get(sessionName).put(token, role);

            return token;
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            throw e;
        }

    }

    public Long findRoomId(Long roomcode) {
        return 0L;
    }
}
