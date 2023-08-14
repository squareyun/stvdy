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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.dto.user.UserDto;
import com.ssafy.ssap.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {
	private static final Logger logger = LoggerFactory.getLogger(MyPageController.class);
	private final UserService userService;

	/**
	 * 비밀번호 변경
	 */
	@PutMapping("/findpwd")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<Map<String, Object>> updatePwd(@RequestBody Map<String, String> map) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			String userEmail = userService.getMyUserWithAuthorities().getEmail();
			userService.updatePassword(userEmail, map.get("currentPassword"), map.get("newPassword"));
			logger.info("비밀번호 변경 성공: userEmail = {}", userEmail);
			resultMap.put("message", MessageFormat.SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("비밀번호 변경 실패: ", e);
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/**
	 * 닉네임 변경
	 */
	@PutMapping("/nickname")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<Map<String, Object>> updateNickname(@RequestBody Map<String, String> map) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			String userNickname = userService.updateNickname(map.get("nickname"));
			logger.info("닉네임 변경 성공: userNickname = {}", userNickname);
			resultMap.put("message", MessageFormat.SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("닉네임 변경 실패: ", e);
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PutMapping("/api-key")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<Map<String, Object>> updateApiKey(@RequestBody Map<String, String> map) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			String apiKey = userService.updateApiKey(map.get("apiKey"));
			logger.info("api-key 변경 성공: apiKey = {}", apiKey);
			resultMap.put("message", MessageFormat.SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("api-key 변경 실패: ", e);
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@DeleteMapping("/api-key")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<Map<String, Object>> deleteApiKey() {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			userService.deleteApiKey();
			logger.info("api-key 삭제 성공");
			resultMap.put("message", MessageFormat.SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("api-key 삭제 실패");
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/**
	 * 회원(본인) 정보 조회
	 * 접근: 로그인한 유저, 관리자
	 */
	@GetMapping("/")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<?> getMyUserInfo() {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			UserDto userDto = userService.getMyUserWithAuthorities();
			logger.info("회원(본인) 정보 조회 성공: userEmail = {}", userDto.getEmail());
			resultMap.put("user", userDto);
			resultMap.put("message", MessageFormat.SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("회원(본인) 정보 조회 실패: ", e);
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
