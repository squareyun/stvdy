package com.ssafy.ssap.controller;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.dto.QuestionCreateDto;
import com.ssafy.ssap.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/questions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final QuestionService questionService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> add(@RequestBody QuestionCreateDto questionCreateDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            Long questionId = questionService.create(questionCreateDto);
            logger.debug("{} 질문 생성 성공", questionId);
            resultMap.put("message", MessageFormat.SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("질문 생성 실패: ", e);
            resultMap.put("message", MessageFormat.SERVER_FAIL);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PutMapping("/{questionNo}")
    public ResponseEntity<Map<String, Object>> add(@PathVariable("questionNo") Long questionNo, @RequestBody QuestionCreateDto questionCreateDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            questionService.update(questionNo, questionCreateDto);
            logger.debug("{} 질문 수정 성공", questionNo);
            resultMap.put("message", MessageFormat.SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("질문 수정 실패: ", e);
            resultMap.put("message", MessageFormat.SERVER_FAIL);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @DeleteMapping("/{questionNo}")
    public ResponseEntity<Map<String, Object>> add(@PathVariable("questionNo") Long questionNo) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            questionService.delete(questionNo);
            logger.debug("{} 질문 삭제 성공", questionNo);
            resultMap.put("message", MessageFormat.SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("질문 삭제 실패: ", e);
            resultMap.put("message", MessageFormat.SERVER_FAIL);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
