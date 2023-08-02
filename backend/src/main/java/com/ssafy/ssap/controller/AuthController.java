package com.ssafy.ssap.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.dto.TokenDto;
import com.ssafy.ssap.dto.user.LoginRequestDto;
import com.ssafy.ssap.dto.user.LoginResponseDto;
import com.ssafy.ssap.jwt.JwtFilter;
import com.ssafy.ssap.jwt.TokenProvider;
import com.ssafy.ssap.service.EmailService;
import com.ssafy.ssap.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final UserService userService;
	private final EmailService emailService;

	/**
	 * 로그인
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
			Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = tokenProvider.createToken(authentication);

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

			LoginResponseDto loginResponseDto = userService.getUserWithEmail(loginRequestDto.getEmail());

			resultMap.put("message", MessageFormat.SUCCESS);
			resultMap.put("user", loginResponseDto);
			resultMap.put("jwt", new TokenDto(jwt));

			logger.info("로그인 성공: userEmail = {}", loginResponseDto.getEmail());
			status = HttpStatus.ACCEPTED;
			return new ResponseEntity<>(resultMap, httpHeaders, status);
		} catch (Exception e) {
			logger.error("로그인 실패: ", e);
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/**
	 * 이메일 인증번호 전송
	 */
	@GetMapping("/join/{email}")
	public ResponseEntity<?> sendEmailPath(@PathVariable String email) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			if (!emailService.sendEmail(email)) {
				logger.warn("인증번호 전송 실패: 이미 존재하는 사용자");
				resultMap.put("message", "Already exists");
				status = HttpStatus.CONFLICT;
			} else {
				logger.info("인증번호 전송 성공: userEmail = {}", email);
				resultMap.put("message", MessageFormat.SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("인증번호 전송 실패: ", e);
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
