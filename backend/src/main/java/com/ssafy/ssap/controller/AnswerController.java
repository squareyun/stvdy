package com.ssafy.ssap.controller;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.dto.AnswerCreateDto;
import com.ssafy.ssap.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
                    .body(Collections.singletonMap("message", MessageFormat.SERVER_FAIL + ": " + e.getClass().getSimpleName()));
        }
    }

    @PutMapping("/{answerNo}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("answerNo") Integer answerNo, @RequestBody AnswerCreateDto answerCreateDto) {
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

}
