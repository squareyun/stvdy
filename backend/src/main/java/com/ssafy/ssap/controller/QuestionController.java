package com.ssafy.ssap.controller;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.dto.QuestionCreateDto;
import com.ssafy.ssap.dto.QuestionDetailResponseDto;
import com.ssafy.ssap.dto.QuestionListResponseDto;
import com.ssafy.ssap.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            Integer questionId = questionService.create(questionCreateDto);
            logger.debug("{} 질문 생성 성공", questionId);
            resultMap.put("message", MessageFormat.SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("질문 생성 실패: ", e);
            resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PutMapping("/{questionNo}")
    public ResponseEntity<Map<String, Object>> add(@PathVariable("questionNo") Integer questionNo, @RequestBody QuestionCreateDto questionCreateDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            Integer updateQuestionId = questionService.update(questionNo, questionCreateDto);
            logger.debug("{} 질문 수정 성공", updateQuestionId);
            resultMap.put("message", MessageFormat.SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("질문 수정 실패: ", e);
            resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @DeleteMapping("/{questionNo}")
    public ResponseEntity<Map<String, Object>> add(@PathVariable("questionNo") Integer questionNo) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            questionService.delete(questionNo);
            logger.debug("{} 질문 삭제 성공", questionNo);
            resultMap.put("message", MessageFormat.SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("질문 삭제 실패: ", e);
            resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/{questionNo}")
    public ResponseEntity<Map<String, Object>> detail(@PathVariable("questionNo") Integer questionNo) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            QuestionDetailResponseDto detail = questionService.detail(questionNo);
            if (detail != null) {
                logger.debug("{}번 질문 조회 성공", questionNo);
                resultMap.put("message", MessageFormat.SUCCESS);
                resultMap.put("question", detail);
            } else {
                logger.debug("{}번 질문 조회 실패 (존재하지 않음)", questionNo);
                resultMap.put("message", MessageFormat.FAIL + ": " + MessageFormat.NO_QUETION_ID);
                resultMap.put("question", detail);
            }
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("질문 조회 실패: ", e);
            resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> add(@RequestParam(required = false) String keyword, @RequestParam(required = false) String nickname, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            Page<QuestionListResponseDto> questionList = questionService.getList(keyword, nickname, pageable);
            logger.debug("{} 개의 질문 검색 성공", questionList.stream().count());
            resultMap.put("question", questionList);
            resultMap.put("message", MessageFormat.SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("질문 검색 실패: ", e);
            resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
