package com.ssafy.ssap.service;

import com.ssafy.ssap.domain.studyroom.Participants;
import com.ssafy.ssap.domain.studyroom.ParticipantsRoleNs;
import com.ssafy.ssap.domain.studyroom.Room;
import com.ssafy.ssap.domain.studyroom.RoomLog;
import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.dto.RoomCreateDto;
import com.ssafy.ssap.dto.RoomDto;
import com.ssafy.ssap.repository.*;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final Logger logger = LoggerFactory.getLogger(RoomService.class);
    private final RoomRepository roomRepository;
    private final ParticipantsRepository participantsRepository;
    private final ParticipantsRoleNsRepository participantsRoleNsRepository;
    private final RoomLogRepository roomLogRepository;
    private final UserRepository userRepository;

    /**
     * OpenVidu variables
     */
    private OpenVidu openVidu;
    @SuppressWarnings("FieldCanBeLocal")
    private final String OPENVIDU_URL = "http://localhost:4443/";
    @SuppressWarnings("FieldCanBeLocal")
    private final String SECRET = "MY_SECRET";

    /**
     * 스터디룸 생성
     */
    @Transactional
    public Integer create(RoomCreateDto roomCreateDto, Session session) {
        // 방 추가
        Room room = Room.builder()
                .title(roomCreateDto.getTitle())
                .quota(roomCreateDto.getQuota())
                .isPrivacy(roomCreateDto.getIsPrivacy())
                .isValid(true)
                .sessionId(session.getSessionId())
                .password(roomCreateDto.getPassword())
                .endTime(LocalDateTime.now().plusHours(roomCreateDto.getEndHour()).plusMinutes(roomCreateDto.getEndMinute()))
                .imagePath(roomCreateDto.getImagePath())
                .rule(roomCreateDto.getRule())
                .build();
        roomRepository.save(room);

        addParticipant(room, "호스트", roomCreateDto.getUserNo());
        addRoomLog(room,roomCreateDto.getUserNo());

        return room.getId();
    }

    public void addParticipant(Room room, String role, Integer userNo){
        // 참여자 추가 (방장)
        Participants participants = Participants.builder()
                .isOut(false)
                .role(participantsRoleNsRepository.findByName(role))
                .room(room)
                .user(userRepository.findById((long)userNo).orElse(null))
                .build();
        participantsRepository.save(participants);
    }

    public void addParticipant(Integer roomNo, String role, Integer userNo) {
        Room room = roomRepository.findById(roomNo).orElse(null);
        addParticipant(room, role, userNo);
    }

    public void addRoomLog(Room room, Integer userId){
        User user = userRepository.findById((long)userId).orElse(null);
        // 접속 기록 추가
        RoomLog roomLog = RoomLog.builder()
                .roomTitle(room.getTitle())
                .enterTime(LocalDateTime.now())
                .room(room)
                .user(user)
                .build();
        roomLogRepository.save(roomLog);
    }

    @SuppressWarnings("DataFlowIssue")
    public void addRoomLog(Integer roomNo, Integer userNO) {
        try{
            Room room = roomRepository.findById(roomNo).orElse(null);
            addRoomLog(room, userNO);
        } catch(NullPointerException e){
            logger.error("roomNo와 Room객체 매칭 실패");
            throw e;
        }
    }

    /**
     * 스터디룸 폐쇄
     */
    @SuppressWarnings("DataFlowIssue")
    @Transactional
    public void close(Integer roomNo) throws OpenViduJavaClientException, OpenViduHttpException {
        // 방에 접속한 사람들의 room_log 데이터 업데이트
        roomLogRepository.updateSpendHourByAllRoomId(roomNo);
        participantsRepository.deleteByRoomId(roomNo);

        roomRepository.setValidToZeroByRoomId(roomNo);

        //OpenVIDU 세션 close
        Room room = roomRepository.findById(roomNo).orElse(null);
        Session session;
        try{
            String sessionId = room.getSessionId();
            session = openVidu.getActiveSession(sessionId);
            session.close();
        } catch(NullPointerException e){
            logger.error("can't find room by roomNo");
            throw e;
        } catch (OpenViduJavaClientException | OpenViduHttpException e2) {
            logger.error("Openvidu session을 닫는 중 에러 발생");
            throw e2;
        }
    }

    public Map<String,Object> makeSession() {
        //session 생성, connection 생성, 토큰 생성
        Map<String, Object> resultMap = new HashMap<>();
        Session session;
        String token;
        try {
            openVidu = new OpenVidu(OPENVIDU_URL,SECRET);
            session = this.openVidu.createSession(); //오픈비두 서버에 세션 생성
            logger.info("session created, "+session+" / "+session.getSessionId());
            token = joinSession(session.getSessionId()); //joinSession에서 connection 생성
            logger.info("connection created, "+token);
            resultMap.put("session",session);
            resultMap.put("token",token);
            return resultMap;
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("DataFlowIssue")
    public String getSessionIdByRoomNo(Integer roomNo) {
        Room room = roomRepository.findById(roomNo).orElse(null);
        if(room==null) logger.error(roomNo+"에 해당하는 방이 DB에 존재하지 않음");
        return room.getSessionId();
    }

    public String joinSession(String sessionId) {
        //이미 열려있는 세션에 참가하고 토큰 반환
        String token=null;

        //입장 요청한 session이 존재하는지 확인 (무조건 존재해야함!)
        Session session = openVidu.getActiveSession(sessionId);
        if (session == null) {
            logger.error("session Id not matching");
            return null;
        }

        //커넥션 생성 및 토큰 반환
        try {
            ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).build();
            token = session.createConnection(connectionProperties).getToken();
            logger.debug("joinRoom token 생성 완료"+token);
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            logger.error("can't build connection properties or get token from it");
        }

        return token;
    }

    public Integer findRoomId(Integer roomcode) {
        return 0;
    }

    public boolean checkValid(Integer roomNo, String password) throws RuntimeException {
        //방 입장이 가능한지 확인하는 메소드
        logger.debug("[join] checkValid메소드 호출. roomNo:"+roomNo+" password:"+password);
        Room room = roomRepository.findById(roomNo).orElse(null);

        try {
            //noinspection DataFlowIssue
            if (participantsRepository.countByRoomIdAndIsOut(roomNo, false) >= room.getQuota()) {
                //check quota
                logger.debug(roomNo + " room is full");
                return false;
            } else {
                logger.debug(roomNo + " room is not full");
            }
            if ((room.getPassword()!=null && password != null) && !room.getPassword().isBlank() && !password.isBlank()) {
                logger.debug("roomPassword, 입력password 둘 다 존재");
                //check password
                if (!room.getPassword().equals(password)) {
                    logger.debug("password 불일치" + room.getPassword() + " / " + password);
                    return false;
                } else {
                    logger.debug("password 일치");
                }
            }
            return true;
        } catch (NullPointerException e){
            logger.error("roomNo와 room 객체 매칭 실패"+roomNo);
            throw e;
        }
    }

    @Transactional
    public void changeHost(Map<String, Integer> changeInfo) {
        /*
          changeInfo.get("roomNo"), changeInfo.get("currentUserNo"), changeInfo.get("nextUserNo")
          participants테이블의 room_id = roomNo and user_id = nextUserNo 조건에 해당하는 유저의 role을 `방장`으로 바꾼다.
          + 권한부여
          room_id = roomNo and user_id = currentUserNo에 해당하는 유저의 role을 일반으로 바꾼다.
         */
        Integer currentUserNo = changeInfo.get("currentUserNo");
        Integer nextUserNo = changeInfo.get("nextUserNo");
        logger.info("userNo"+currentUserNo+"/participantsNo:"+nextUserNo);

        Participants currentHost = participantsRepository.findById(currentUserNo).orElse(null);
        Participants nextHost = participantsRepository.findById(nextUserNo).orElse(null);
        final ParticipantsRoleNs ROLE_HOST = participantsRoleNsRepository.findByName("호스트");

        try{
            //noinspection DataFlowIssue
            if(currentHost.getRole()==ROLE_HOST && nextHost.getRole()!=ROLE_HOST){
                currentHost.setRole(nextHost.getRole());
//                participantsRepository.save(currentHost);
                nextHost.setRole(ROLE_HOST);
//                participantsRepository.save(nextHost);
                logger.info("호스트 role 변경 완료. partiNo "+currentUserNo+" to "+currentHost.getRole().toString()+", partiNo "+nextUserNo+" to "+nextHost.getRole().toString());
            }
        } catch (NullPointerException e) {
            logger.error("user_id와 참여자/호스트 매칭 실패");
            throw e;
        }

    }

    public void assignStaff(Integer participantNo) {
        Participants assignee = participantsRepository.findById(participantNo).orElse(null);

        try{
            //noinspection DataFlowIssue
            if(assignee.getRole() == participantsRoleNsRepository.findByName("참여자")){
                assignee.setRole(participantsRoleNsRepository.findByName("스태프"));
//                participantsRepository.save(assignee);
            } else{
                logger.error("스태프로 임명 가능한 role이 아님");
            }
        }catch (NullPointerException e){
            logger.error("participantNo와 참여자 매칭 실패");
            throw e;
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @Transactional
    public HttpStatus exit(Integer roomNo, Integer participantNo) {
        /*
        1. openvidu connection close
        2. room_log 수정
        3. participant 테이블 수정
         */
        String connectionId;
        Session session;
        HttpStatus status;

        RoomLog roomLog = roomLogRepository.findByRoomIdAndUserId(roomNo, participantNo).orElse(null);
        Participants participant = participantsRepository.findById(participantNo).orElse(null);


        try{
            //대상자 connection 강제 disconnect
            connectionId = participant.getConnectionId();
            session = openVidu.getActiveSession(roomLog.getRoom().getSessionId());
            session.forceDisconnect(connectionId);

            if(session.getConnection(connectionId) == null) {
                //강제 disconnect 성공 시 db처리

                //participant 테이블 수정 (is_Out 수정)
                participant.setIsOut(true);
                logger.trace("is_out 수정 완료");

                //room_log 수정 (spend hour 갱신)
                LocalDateTime time_start, time_end;
                time_start = roomLog.getEnterTime();
                time_end = LocalDateTime.now();
                int spend_min = Math.toIntExact(ChronoUnit.MINUTES.between(time_start, time_end));
                LocalTime spend_time = LocalTime.of(spend_min / 60, spend_min % 60);
                roomLog.setSpendHour(spend_time);
                logger.trace("spend hour 갱신 완료");
                status = HttpStatus.OK;
            } else{
                logger.error("openvidu connection이 남아있음");
                status = HttpStatus.CONFLICT;
            }
        } catch(NullPointerException e){
            logger.error("매칭되는 객체 없음");
            status = HttpStatus.CONFLICT;
        } catch(OpenViduJavaClientException | OpenViduHttpException e){
            logger.error("Openvidu connection 처리 실패");
            status = HttpStatus.CONFLICT;
        }
        logger.debug("room/exit 트랜잭션 정상 완료");
        return status;
    }


    public List<RoomDto> getRoomList() {
//        if(keyword==null) System.out.println("keyword is null");
//        if(page==null) System.out.println("page is null");
        logger.trace("getRoomList 접근");
        List<RoomDto> list = roomRepository.findAllRooms();
//        ListIterator<Room> li = list.listIterator();

        logger.debug(list.get(0).toString());
        return list; //키워드와 페이지 검색 수정필요
    }

    @Transactional
    public void kickAndAlarm(Integer roomNo, Integer participantNo, String reason) {
        /*
        participants테이블의 participantsNo가 일치하는 유저의 is_out을 0으로 바꾼다.
        alarm 테이블에 insert (insert into alarm(title, detail, link, user_id) values("강제퇴장 처리", kickinfo.reason, ?, kickInfo.partiNo))
        room_log 테이블에 update (update room_log set exit_time=now() where user_id= ~ and room_id = ~)
        */

        if(exit(roomNo,participantNo) == HttpStatus.OK);
        //alarmService.createAlarm(room.getTitle()+"방에서 내보내졌습니다.", reason, participant.getUserId());


    }
}

