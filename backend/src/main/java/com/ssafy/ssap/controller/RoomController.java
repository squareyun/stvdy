package com.ssafy.ssap.controller;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.dto.RoomCreateDto;
import com.ssafy.ssap.service.RoomService;
<<<<<<< HEAD
import lombok.RequiredArgsConstructor;
=======
import io.openvidu.java.client.OpenViduException;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
>>>>>>> beom
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
=======

>>>>>>> beom
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
<<<<<<< HEAD
    public ResponseEntity<Map<String, Object>> add(@RequestBody RoomCreateDto roomCreateDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            Integer roomId = roomService.create(roomCreateDto);
            logger.debug("{} 스터디룸 생성 성공", roomId);
            resultMap.put("message", MessageFormat.SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("스터디룸 생성 실패: ", e);
            resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @DeleteMapping("/{roomno}")
    public ResponseEntity<?> close(@PathVariable("roomno") Integer roomNo) {
=======
    public ResponseEntity<String> add(@RequestBody RoomCreateDto roomCreateDto) {
        System.out.println("/rooms/add 진입");
        System.out.println(roomCreateDto.toString());
        HttpStatus status = null;
        String token = null;
        try {
            //session, connection 생성 후 token 받아오기
            token = roomService.makeSession(roomCreateDto);

            //세션 생성 성공 시 db에 입력
            Long roomId = roomService.create(roomCreateDto);
            logger.debug("{} 스터디룸 생성 성공", roomId);
            status = HttpStatus.ACCEPTED;
//            return new ResponseEntity<>(token, status); //testedit
        } catch (Exception e) {
            logger.error("스터디룸 생성 실패: ", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
//        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        return new ResponseEntity<>(token, status); //testedit
    }

    @DeleteMapping("/{roomno}")
    public ResponseEntity<?> close(@PathVariable("roomno") Long roomNo) {
>>>>>>> beom
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            roomService.close(roomNo);
            logger.debug("{} 스터디룸 폐쇄 성공", roomNo);
            resultMap.put("message", MessageFormat.SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("스터디룸 폐쇄 실패: ", e);
<<<<<<< HEAD
            resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
=======
            resultMap.put("message", MessageFormat.SERVER_FAIL);
>>>>>>> beom
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
<<<<<<< HEAD
=======

    @GetMapping("/rooms/{roomno}")
    public void join(){


        return;
    }
>>>>>>> beom
}
