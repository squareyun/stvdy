package com.ssafy.ssap.controller;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.dto.RoomCreateDto;
import com.ssafy.ssap.service.RoomService;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
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
    public ResponseEntity<?> add(@RequestBody RoomCreateDto roomCreateDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            //openvidu session, connection 생성
            roomService.makeSession(roomCreateDto, resultMap); //openviduDto에 session, connection, token 담음
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("스터디룸 생성 실패: ", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
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
    public ResponseEntity<?> changeHost(@RequestBody Map<String, Integer> changeInfo){
        /* roomNo, currentUserNo, nextUserNo */
        HttpStatus status;
        logger.debug("host 변경 controller 호출");
        try{
            roomService.changeHost(changeInfo.get("roomNo"), changeInfo.get("currentUserNo"),changeInfo.get("nextUserNo"));
            status = HttpStatus.OK;
        } catch(Exception e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }

    @PutMapping("/role")
    public ResponseEntity<?> assignStaff(@RequestBody Integer roomNo, Integer userNo){
        /*
          assignInfo.get("roomNo"), assignInfo.get("participantsNo")
          participants테이블의 room_id = roomNo and user_id = participantsNo 조건에 해당하는 유저의 role을 `스태프`으로 바꾼다.
          + 권한부여
         */
        HttpStatus status;
        logger.trace("staff 임명 controller 호출 with "+userNo);
        try{
            roomService.assignStaff(roomNo, userNo);
            status = HttpStatus.OK;
        } catch (Exception e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }

    @Transactional
    @PostMapping("/kick")
    public ResponseEntity<?> kick(@RequestBody Map<String, Object> kickInfo){
        /*
          kickInfo.get("roomNo"), kickInfo.get("participantsNo"), kickInfo.get("reason")
         */
        logger.debug("rooms/kick controller 호출 with : "+kickInfo.toString());
        HttpStatus status;
        try{
            roomService.kickAndAlarm(
                    (Integer) kickInfo.get("roomNo"),
                    (Integer) kickInfo.get("participantsNo"),
                    (String) kickInfo.get("reason")
            );
            status = HttpStatus.OK;
        } catch(Exception e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }

    @GetMapping("/code/{roomNo}")
    public ResponseEntity<?> getCodeAndLink(@PathVariable Long roomNo){
        /*
          roomno 기반으로 code와 link 리턴.
          roomno 기반으로 code와 link에 대한 정의 필요
         */
        logger.trace(roomNo+"방의 코드 생성 요청");
        return new ResponseEntity<>("code",HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String,Object>> search(){
        /*
          keyword로 where = keyword 검색 쿼리 날린 결과 돌려주기
          @return : roomNo(int) roomTitle(String) quota(int) participantsCnt(int) roomImagePath(String)
         * pageLimit(미정) 개수만큼 자르기
         */
        HttpStatus status;
        Map<String,Object> resultMap = new HashMap<>();
        try {
            roomService.getRoomList(resultMap);
            status = HttpStatus.OK;
        } catch(Exception e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @Deprecated
    @GetMapping("/detail/{roomNo}")
    public ResponseEntity<?> detail(@PathVariable Integer roomNo){
        /*
          roomNo에 해당하는 room에 대한 정보(roomDto) return
          participant 정보가 필요하여 넘겨주는 것이 아니면 구현X
         */
        logger.trace(roomNo+"방의 정보 요청");
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Transactional
    @PostMapping("/{roomNo}")
    public ResponseEntity<?> join(@PathVariable Integer roomNo, @RequestBody Map<String,Object> map) {
        /*
          1. 해당하는 룸넘버가 입장가능한지 조회 (비밀번호, 정원(+강퇴당했었는지?))
          2. 룸넘버로 세션아이디 쿼리조회
          3. 세션아이디로 커넥션 생성 및 입장토큰 획득 ->프론트로 리턴
          4. participants 테이블 insert
          5. room_log 테이블 insert
         */
        HttpStatus status;
        String token;

        try{
            token = roomService.joinRoom(
                    roomNo,
                    (String) map.get("password"),
                    (Integer) map.get("userNo")
            );
            status = HttpStatus.OK;
        } catch(Exception e){
            token = null;
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(token, status);
    }

//    @GetMapping("/code/{roomcode}")
//    public String joinByCode(@PathVariable Integer roomcode){
//        Integer roomId = roomService.findRoomId(roomcode);
//        return join(roomId);
//    }

    @PostMapping("exit")
    public ResponseEntity<?> exit(@RequestBody Map<String, Integer>map) throws OpenViduJavaClientException, OpenViduHttpException {
        HttpStatus status = roomService.exit(map.get("roomNo"), map.get("userNo"));
        return new ResponseEntity<>(status);
    }

    @GetMapping("/currentConnection/{roomno}")
    public ResponseEntity<?> checkConnection(@PathVariable Integer roomNo){
        HttpStatus status;
        Map<String, Object> resultMap;
        try{
            resultMap = roomService.checkConnection(roomNo);
            status = HttpStatus.OK;
        } catch(Exception e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            resultMap = new HashMap<>();
            resultMap.put("meessage","connection 확인 중 실패");
        }
        return new ResponseEntity<>(resultMap, status);
    }
}
