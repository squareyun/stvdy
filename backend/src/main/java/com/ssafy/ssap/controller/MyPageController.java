package com.ssafy.ssap.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssap.common.MessageFormat;
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
}
