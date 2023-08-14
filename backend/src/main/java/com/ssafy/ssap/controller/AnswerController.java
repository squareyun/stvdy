package com.ssafy.ssap.controller;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.dto.AnswerCreateDto;
import com.ssafy.ssap.dto.AnswerResponseDto;
import com.ssafy.ssap.dto.LikesDto;
import com.ssafy.ssap.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/answers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class AnswerController {

    private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);
    private final AnswerService answerService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> add(@RequestBody AnswerCreateDto answerCreateDto) {
        try {
            Integer answerId = answerService.create(answerCreateDto);
            logger.debug("질문 {} 에 대한 답변 {} 생성 성공", answerCreateDto.getQuestionNo(), answerId);
            return ResponseEntity.accepted()
                    .body(Collections.singletonMap("message", MessageFormat.SUCCESS));
        } catch (Exception e) {
            logger.error("답변 생성 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message",
                            MessageFormat.SERVER_FAIL + ": " + e.getMessage()));
        }
    }

    @PutMapping("/{answerNo}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("answerNo") Integer answerNo,
                                                      @RequestBody AnswerCreateDto answerCreateDto) {
        try {
            Integer answerId = answerService.update(answerNo, answerCreateDto);
            logger.debug("답변 {} 수정 성공", answerId);
            return ResponseEntity.accepted()
                    .body(Collections.singletonMap("message", MessageFormat.SUCCESS));
        } catch (Exception e) {
            logger.error("답변 수정 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage()));
        }
    }

    @GetMapping("/list/{questionNo}")
    public ResponseEntity<Map<String, Object>> getList(@PathVariable Integer questionNo) {
        try {
            List<AnswerResponseDto> answerList = answerService.getList(questionNo);
            logger.debug("질문 {} 에 대한 답변 목록 {}개 조회 성공", questionNo, (long) answerList.size());
            return ResponseEntity.accepted()
                    .body(Map.of("answers", answerList, "message", MessageFormat.SUCCESS));
        } catch (Exception e) {
            logger.error("질문 {} 에 대한 답변 조회 실패: {}", questionNo, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage()));
        }
    }

    @PutMapping("/likes/{answerNo}")
    public ResponseEntity<Map<String, Object>> addLikes(@PathVariable("answerNo") Integer answerNo,
                                                        @RequestBody LikesDto likesDto) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        try {
            answerService.updateLikes(answerNo, likesDto);
            logger.debug("답변 좋아요 성공");
            response.put("message", MessageFormat.SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("답변 좋아요 실패", e);
            response.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(status).body(response);
    }

    @PutMapping("/choose/{questionNo}/{answerNo}")
    public ResponseEntity<Map<String, Object>> selectAnswer(@PathVariable("questionNo") Integer questionNo,
                                                            @PathVariable("answerNo") Integer answerNo) {

        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        try {
            answerService.selectAnswer(questionNo, answerNo);
            logger.debug("답변 채택 성공");
            response.put("message", MessageFormat.SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("답변 채택 실패", e);
            response.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(status).body(response);
    }

}
