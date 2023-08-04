package com.ssafy.ssap.controller;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.dto.RoomCreateDto;
import com.ssafy.ssap.service.RoomService;
import io.openvidu.java.client.Session;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final RoomService roomService;


    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody RoomCreateDto roomCreateDto) {
        try {
            //session, connection 생성
            Map<String, Object> resultMap = roomService.makeSession();

            //세션 생성 성공 시 db에 입력
            Integer roomId = roomService.create(roomCreateDto, (Session) resultMap.get("session"));
            logger.info("{} 스터디룸 생성 성공", roomId);
            return new ResponseEntity<>((String) resultMap.get("token"), HttpStatus.ACCEPTED); //testedit
        } catch (Exception e) {
            logger.error("스터디룸 생성 실패: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{roomno}")
    public ResponseEntity<?> close(@PathVariable("roomno") Integer roomNo) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            roomService.close(roomNo);
            logger.debug("{} 스터디룸 폐쇄 성공", roomNo);
            resultMap.put("message", MessageFormat.SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("스터디룸 폐쇄 실패: ", e);
            resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @PutMapping("/host")
    public void changeHost(@RequestBody Map<String, Integer> changeInfo){
        /* currentUserNo, nextUserNo */
        logger.debug("host 변경 controller 호출");
        roomService.changeHost(changeInfo);
    }

    @PutMapping("/role")
    public void assignStaff(@RequestBody Integer participantNo){
        /*
          assignInfo.get("roomNo"), assignInfo.get("participantsNo")
          participants테이블의 room_id = roomNo and user_id = participantsNo 조건에 해당하는 유저의 role을 `스태프`으로 바꾼다.
          + 권한부여
         */
        logger.debug("staff 임명 controller 호출 with "+participantNo);
        roomService.assignStaff(participantNo);
    }

    @Transactional
    @PostMapping("/kick")
    public void kick(@RequestBody Map<String, Object> kickInfo){
        /*
          kickInfo.get("roomNo"), kickInfo.get("participantsNo"), kickInfo.get("reason")
          participants테이블의 room_id = roomNo and user_id = participantsNo 조건에 해당하는 유저의 is_out을 0으로 바꾼다.
          alarm 테이블에 insert (insert into alarm(title, detail, link, user_id) values("강제퇴장 처리", kickinfo.reason, ?, kickInfo.partiNo))
          room_log 테이블에 update (update room_log set exit_time=now() where user_id= ~ and room_id = ~)
         */
        System.out.println(kickInfo.toString());
    }

    @GetMapping("/code/{roomno}")
    public ResponseEntity<?> getCodeAndLink(@PathVariable Long roomNo){
        /*
          roomno 기반으로 code와 link 리턴.
          roomno 기반으로 code와 link에 대한 정의 필요
         */
        System.out.println(roomNo);
        return new ResponseEntity<>("code",HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/list")
    public ResponseEntity<?> search(@RequestParam String keyword, @RequestParam Integer page){
        /*
          keyword로 where = keyword 검색 쿼리 날린 결과 돌려주기
          @return : roomNo(int) roomTitle(String) quota(int) participantsCnt(int) roomImagePath(String)
         * pageLimit(미정) 개수만큼 자르기
         */
//        List<RoomDto> roomList = new ArrayList<RoomDto>();
        System.out.println(keyword+" / "+page);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/detail/{roomno}")
    public ResponseEntity<?> detail(@PathVariable Integer roomNo){
        /*
          roomNo에 해당하는 room에 대한 정보(roomDto) return
         */
        System.out.println(roomNo);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Transactional
    @PostMapping("/{roomNo}")
    public String join(@PathVariable Integer roomNo, @RequestBody Map<String,String> map) {
        /*
          1. 해당하는 룸넘버가 입장가능한지 조회 (비밀번호, 정원(+강퇴당했었는지?))
          2. 룸넘버로 세션아이디 쿼리조회
          3. 세션아이디로 커넥션 생성 및 입장토큰 획득 ->프론트로 리턴
          4. participants 테이블 insert
          5. room_log 테이블 insert
         */
        String token=null;
        String sessionId;
        String password = map.get("password");
        Integer userId = Integer.parseInt(map.get("userId"));
        if( roomService.checkValid(roomNo, password)){ //1번 단계
            //OPENVIDU > session 접속을 위한 token 생성
            sessionId = roomService.getSessionIdByRoomNo(roomNo); //2번 단계
            token = roomService.joinSession(sessionId); //3번 단계

            //DB처리
            roomService.addParticipant(roomNo,"참여자", userId); //4번
            roomService.addRoomLog(roomNo,userId); //5번
        }

        return token;
    }

//    @GetMapping("/code/{roomcode}")
//    public String joinByCode(@PathVariable Integer roomcode){
//        Integer roomId = roomService.findRoomId(roomcode);
//        return join(roomId);
//    }
}
