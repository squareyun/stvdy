package com.ssafy.ssap.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.dto.user.UserDto;
import com.ssafy.ssap.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;

	/**
	 * 테스트를 위한 코드 : 추후 삭제 예정
	 */
	@GetMapping("/hello")
	public ResponseEntity<?> hello() {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		resultMap.put("welcomeMessage", "hello!");
		resultMap.put("message", MessageFormat.SUCCESS);
		status = HttpStatus.ACCEPTED;

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/**
	 * 회원가입
	 */
	@PostMapping("/join")
	public ResponseEntity<?> join(@Valid @RequestBody UserDto userDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			UserDto user = userService.join(userDto);
			logger.info("회원가입 성공: userEmail = {}", user.getEmail());
			resultMap.put("message", MessageFormat.SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("회원가입 실패: ", e);
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/**
	 * 회원탈퇴
	 */
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> withdrawal(@PathVariable Integer userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			User user = userService.withdrawal(userId);
			logger.info("회원탈퇴 성공: userEmail = {}", user.getEmail());
			resultMap.put("message", MessageFormat.SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("회원탈퇴 실패: ", e);
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/**
	 * 특정 회원 조회
	 * 접근: 관리자
	 */
	@GetMapping("/user/{email}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> getUserInfo(@PathVariable String email) {
		// return ResponseEntity.ok(userService.getUserWithAuthorities(email));
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			UserDto userDto = userService.getMyUserWithAuthorities();
			logger.info("특정 회원 조회 성공: userEmail = {}", userDto.getEmail());
			resultMap.put("message", MessageFormat.SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.info("특정 회원 조회 실패");
			logger.error("특정 회원 조회 실패: ", e);
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
