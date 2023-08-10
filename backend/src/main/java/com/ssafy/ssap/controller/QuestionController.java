package com.ssafy.ssap.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.dto.LikesDto;
import com.ssafy.ssap.dto.QuestionCreateDto;
import com.ssafy.ssap.dto.QuestionDetailResponseDto;
import com.ssafy.ssap.dto.QuestionListResponseDto;
import com.ssafy.ssap.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/questions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class QuestionController {

	private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
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
			logger.error("질문 생성 실패: {}", e.getMessage());
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getClass().getSimpleName());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PutMapping("/{questionNo}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable("questionNo") Integer questionNo,
		@RequestBody QuestionCreateDto questionCreateDto) {
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
	public ResponseEntity<Map<String, Object>> delete(@PathVariable("questionNo") Integer questionNo) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			questionService.delete(questionNo);
			logger.debug("{} 질문 삭제 성공", questionNo);
			resultMap.put("message", MessageFormat.SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("질문 삭제 실패: {}", e.getMessage());
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getClass().getSimpleName());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/{questionNo}")
	public ResponseEntity<Map<String, Object>> detail(@PathVariable("questionNo") Integer questionNo) {
		try {
			QuestionDetailResponseDto detail = questionService.detail(questionNo);
			if (detail != null) {
				logger.debug("{}번 질문 단건 조회 성공", questionNo);
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("message", MessageFormat.SUCCESS);
				resultMap.put("question", detail);
				return ResponseEntity.ok(resultMap);
			} else {
				logger.debug("{}번 질문 단건 조회 실패 (존재하지 않음)", questionNo);
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Collections.singletonMap("message", MessageFormat.FAIL + ": " + MessageFormat.NO_QUETION_ID));
			}
		} catch (Exception e) {
			logger.error("질문 단건 조회 실패", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Collections.singletonMap("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage()));
		}
	}

	@GetMapping("/islike/{userNo}/{questionNo}")
	public ResponseEntity<Map<String, Object>> detailIsLike(@PathVariable("userNo") Integer userNo,
		@PathVariable("questionNo") Integer questionNo) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			Boolean isLike = questionService.getIsLike(userNo, questionNo);
			logger.debug("질문 isLiked 조회 성공");
			resultMap.put("message", MessageFormat.SUCCESS);
			resultMap.put("isQuetionLiked", isLike);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("질문 isLiked 조회 실패", e);
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getClass().getSimpleName());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/**
	 * 질문 목록 조회
	 * 전체, 키워드 검색, 닉네임 검색, 페이징 기능 지원
	 */
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> list(@RequestParam(required = false) String keyword,
		@RequestParam(required = false) String nickname,
		@RequestParam(required = false, defaultValue = "false") Boolean noAnsFilter,
		@RequestParam(required = false, defaultValue = "false") Boolean noBestAnsFilter,
		Pageable pageable) {
		try {
			Page<QuestionListResponseDto> questionList = questionService.getList(keyword, nickname, noAnsFilter,
				noBestAnsFilter, pageable);
			logger.debug("{} 개의 질문 검색 성공", questionList.stream().count());

			return ResponseEntity.accepted()
				.body(Map.of("question", questionList, "message", MessageFormat.SUCCESS));

		} catch (Exception e) {
			logger.error("질문 검색 실패", e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage()));
		}
	}

	/**
	 * 질문 목록 조회
	 * 특정 유저의 질문만 검색
	 */
	@GetMapping("/list/my")
	public ResponseEntity<Map<String, Object>> listWithUserno(@RequestParam(name = "userno") Integer userNo, Pageable pageable) {
		try {
			Page<QuestionListResponseDto> questionList = questionService.getListWithUserNo(userNo, pageable);
			logger.debug("{} 개의 질문 검색 성공 userNo: {}", questionList.stream().count(), userNo);

			return ResponseEntity.accepted()
				.body(Map.of("question", questionList, "message", MessageFormat.SUCCESS));

		} catch (Exception e) {
			logger.error("질문 검색 실패", e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage()));
		}
	}

	@PutMapping("/likes/{questionNo}")
	public ResponseEntity<Map<String, Object>> addLikes(@PathVariable("questionNo") Integer questionNo,
		@RequestBody LikesDto likesDto) {
		try {
			questionService.updateLikes(questionNo, likesDto);
			logger.debug("질문 좋아요 성공");
			return ResponseEntity.accepted()
				.body(Collections.singletonMap("message", MessageFormat.SUCCESS));
		} catch (Exception e) {
			logger.error("질문 좋아요 실패", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Collections.singletonMap("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage()));
		}
	}

	@GetMapping("/category-list")
	public ResponseEntity<Map<String, Object>> categoryList() {
		try {
			List<String> categoryList = questionService.getCategoryRanking();
			logger.debug("{} 개의 category 조회 성공", categoryList.size());

			return ResponseEntity.accepted()
				.body(Map.of("category", categoryList, "message", MessageFormat.SUCCESS));

		} catch (Exception e) {
			logger.error("category 조회 실패", e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage()));
		}
	}
}
