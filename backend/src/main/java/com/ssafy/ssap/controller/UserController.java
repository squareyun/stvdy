package com.ssafy.ssap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssap.dto.user.UserDto;
import com.ssafy.ssap.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	/*
	 * 테스트를 위한 코드 : 추후 삭제 예정
	 * */
	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("hello");
	}

	/*
	 * 회원가입
	 * */
	@PostMapping("/join")
	public ResponseEntity<UserDto> join(@Valid @RequestBody UserDto userDto) {
		return ResponseEntity.ok(userService.signup(userDto));
	}

	/*
	 * 접근: 로그인한 유저, 관리자만 가능
	 * */
	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<UserDto> getMyUserInfo(HttpServletRequest request) {
		return ResponseEntity.ok(userService.getMyUserWithAuthorities());
	}

	/*
	 * 사용자 정보 조회
	 * 접근: 관리자만 가능
	 * */
	@GetMapping("/user/{email}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<UserDto> getUserInfo(@PathVariable String email) {
		return ResponseEntity.ok(userService.getUserWithAuthorities(email));
	}
}
