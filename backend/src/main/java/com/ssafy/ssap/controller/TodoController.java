package com.ssafy.ssap.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.service.TodoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todolist")
@RequiredArgsConstructor
public class TodoController {

	private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
	private final TodoService todoService;

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

	@PutMapping("/{todoId}")
	public ResponseEntity<Map<String, Object>> add(@PathVariable Integer todoId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			todoService.checkDoneFlag(todoId);
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
}
