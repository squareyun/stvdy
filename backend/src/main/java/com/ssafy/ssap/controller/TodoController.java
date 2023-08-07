package com.ssafy.ssap.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.dto.TodoResponseDto;
import com.ssafy.ssap.service.TodoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todolist")
@RequiredArgsConstructor
public class TodoController {

	private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
	private final TodoService todoService;

	/**
	 * todo 생성
	 */
	@PostMapping("/add")
	public ResponseEntity<Map<String, Object>> add(@RequestBody Map<String, String> map) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			Integer todoId = todoService.create(map.get("objective"));
			logger.info("{} todo 생성 성공", todoId);
			resultMap.put("message", MessageFormat.SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("todo 생성 실패: {}", e.getMessage());
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getClass().getSimpleName());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/**
	 * todo 삭제
	 */
	@DeleteMapping("/{todoId}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer todoId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			todoService.delete(todoId);
			logger.info("{} todo 삭제 성공", todoId);
			resultMap.put("message", MessageFormat.SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("todo 삭제 실패: {}", e.getMessage());
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getClass().getSimpleName());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/**
	 * todo 완료 표시
	 */
	@PutMapping("/{todoId}")
	public ResponseEntity<Map<String, Object>> updateDoneFlag(@PathVariable Integer todoId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			todoService.updateDoneFlag(todoId);
			logger.info("{} todo toggle 표시 성공", todoId);
			resultMap.put("message", MessageFormat.SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("todo toggle 표시 실패: {}", e.getMessage());
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getClass().getSimpleName());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/**
	 * todo 목록 - 일별 조회
	 */
	@GetMapping("/{date}")
	public ResponseEntity<Map<String, Object>> getDailyTodoList(@PathVariable LocalDate date) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			List<TodoResponseDto> todoList = todoService.getDailyTodoList(date);
			logger.info("{} todo 목록 일별 조회 성공", date);
			resultMap.put("message", MessageFormat.SUCCESS);
			resultMap.put("todoList", todoList);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("todo 목록 일별 조회 실패: {}", e.getMessage());
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getClass().getSimpleName());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
