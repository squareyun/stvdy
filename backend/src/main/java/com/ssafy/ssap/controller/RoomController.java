package com.ssafy.ssap.controller;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.dto.RoomCreateDto;
import com.ssafy.ssap.service.RoomService;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final RoomService roomService;

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody RoomCreateDto roomCreateDto) {
        HttpStatus status;
        String token = null;
        try {
            //session, connection 생성 후 token 받아오기
            token = roomService.makeSession(roomCreateDto);

            //세션 생성 성공 시 db에 입력
            Integer roomId = roomService.create(roomCreateDto);
            logger.debug("{} 스터디룸 생성 성공", roomId);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("스터디룸 생성 실패: ", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(token, status); //testedit
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
        /**
         * changeInfo.get("roomNo"), changeInfo.get("userNo"), changeInfo.get("participantsNo")
         * participants테이블의 room_id = roomNo and user_id = participantsNo 조건에 해당하는 유저의 role을 `방장`으로 바꾼다.
         * + 권한부여
         * room_id = roomNo and user_id = userNo에 해당하는 유저의 role을 일반으로 바꾼다.
         */
        System.out.println(changeInfo.toString());
    }

    @PutMapping("/role")
    public void assignStaff(@RequestBody Map<String, Integer> assignInfo){
        /**
         * assignInfo.get("roomNo"), assignInfo.get("participantsNo")
         * participants테이블의 room_id = roomNo and user_id = participantsNo 조건에 해당하는 유저의 role을 `스태프`으로 바꾼다.
         * + 권한부여
         */
        System.out.println(assignInfo.toString());
    }

    @Transactional
    @PostMapping("/kick")
    public void kick(@RequestBody Map<String, Object> kickInfo){
        /**
         * kickInfo.get("roomNo"), kickInfo.get("participantsNo"), kickInfo.get("reason")
         * participants테이블의 room_id = roomNo and user_id = participantsNo 조건에 해당하는 유저의 is_out을 0으로 바꾼다.
         * alarm 테이블에 insert (insert into alarm(title, detail, link, user_id) values("강제퇴장 처리", kickinfo.reason, ?, kickInfo.partiNo))
         * room_log 테이블에 update (update room_log set exit_time=now() where user_id= ~ and room_id = ~)
         */
        System.out.println(kickInfo.toString());
    }

    @GetMapping("/code/{roomno}")
    public ResponseEntity<?> getCodeAndLink(@PathVariable Long roomNo){
        /**
         * roomno 기반으로 code와 link 리턴.
         * roomno 기반으로 code와 link에 대한 정의 필요
         */
        System.out.println(roomNo);
        return new ResponseEntity<>("code",HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/list")
    public ResponseEntity<?> search(@RequestParam String keyword, @RequestParam Integer page){
        /**
         * keyword로 where = keyword 검색 쿼리 날린 결과 돌려주기
         * @return : roomNo(int) roomTitle(String) quota(int) participantsCnt(int) roomImagePath(String)
         * pageLimit(미정) 개수만큼 자르기
         */
//        List<RoomDto> roomList = new ArrayList<RoomDto>();
        System.out.println(keyword+" / "+page);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/detail/{roomno}")
    public ResponseEntity<?> detail(@PathVariable Integer roomNo){
        /**
         * roomNo에 해당하는 room에 대한 정보(roomDto) return
         */
        System.out.println(roomNo);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Transactional
    @GetMapping("/{roomno}")
    public String join(@PathVariable Integer roomNo){
        /**
         * 1. 룸넘버로 세션아이디 쿼리조회
         * 2. 세션아이디 반환
         *  2-1. 세션아이디 반환 전 여분 자리가 있는지 확인. 자리가 없으면 null토큰(자리없음에러?) 반환
         * 3. participants 테이블 insert
         * 4. room_log 테이블 insert
         */
        String token="";
        return token;
    }

    @GetMapping("/code/{roomcode}")
    public String joinByCode(@PathVariable Integer roomcode){
        Integer roomId = roomService.findRoomId(roomcode);
        return join(roomId);
    }
}
