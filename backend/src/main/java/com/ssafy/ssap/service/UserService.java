package com.ssafy.ssap.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ssap.domain.user.Authority;
import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.domain.user.UserStateNs;
import com.ssafy.ssap.dto.user.LoginResponseDto;
import com.ssafy.ssap.dto.user.UserDto;
import com.ssafy.ssap.exception.DuplicateMemberException;
import com.ssafy.ssap.exception.NotFoundMemberException;
import com.ssafy.ssap.repository.UserRepository;
import com.ssafy.ssap.repository.UserStateNsRepository;
import com.ssafy.ssap.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserStateNsRepository userStateNsRepository;

	/**
	 * 회원가입
	 */
	@Transactional
	public UserDto join(UserDto userDto) {
		if (userRepository.findOneWithAuthoritiesByEmail(userDto.getEmail()).orElse(null) != null) {
			throw new DuplicateMemberException("사용할 수 없는 이메일입니다.");
		}

		Authority authority = Authority.builder()
			.authorityName("ROLE_USER")
			.build();
		userDto.setState("사용자");
		// UserStateNs withdrawnState = userStateNsRepository.findByName("사용자");
		User user = User.builder()
			.email(userDto.getEmail())
			.password(passwordEncoder.encode(userDto.getPassword()))
			.name(userDto.getName())
			.nickname(userDto.getNickname())
			.state(new UserStateNs(userDto.getState()))
			.registTime(LocalDateTime.now())
			.authorities(Collections.singleton(authority))
			.activated(true)
			.build();

		return UserDto.from(userRepository.save(user));
	}

	/**
	 * 회원탈퇴
	 */
	@Transactional
	public User withdrawal(Integer userId) {
		User user = userRepository.findById(userId).orElse(null);

		if (user != null) {
			UserStateNs withdrawnState = userStateNsRepository.findByName("탈퇴");
			user.setState(withdrawnState);
			user.setStateTime(LocalDateTime.now());
			userRepository.save(user);
		} else {
			throw new NotFoundMemberException("해당 사용자를 찾을 수 없습니다.");
		}

		// TODO: 추후 jwt refresh 토큰 생성시 만료 처리
		return user;
	}

	@Transactional(readOnly = true)
	public UserDto getUserWithAuthorities(String email) {
		return UserDto.from(userRepository.findOneWithAuthoritiesByEmail(email).orElse(null));
	}

	public LoginResponseDto getUserWithEmail(String email) {
		return LoginResponseDto.from(userRepository.findOneWithAuthoritiesByEmail(email).orElse(null));
	}

	@Transactional(readOnly = true)
	public UserDto getMyUserWithAuthorities() {
		return UserDto.from(
			SecurityUtil.getCurrentUsername()
				.flatMap(userRepository::findOneWithAuthoritiesByEmail)
				.orElseThrow(() -> new NotFoundMemberException("Member not found"))
		);
	}

	/**
	 * email로 id(pk)값 조회
	 */
	public Integer getUserIdByUsername(String email) {
		Optional<User> optionalUser = userRepository.findOneWithAuthoritiesByEmail(email);
		return optionalUser.map(User::getId).orElse(null);
	}

	public boolean isPasswordMatch(String userEmail, String password) {
		User user = userRepository.findByEmail(userEmail);
		return passwordEncoder.matches(password, user.getPassword());
	}

	/**
	 * 비밀번호 변경
	 */
	public void updatePassword(String userEmail, String currentPassword, String newPassword) {
		if (isPasswordMatch(userEmail, currentPassword)) {
			User user = userRepository.findOneWithAuthoritiesByEmail(userEmail)
				.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));
			String encodedPassword = passwordEncoder.encode(newPassword);
			user.setPassword(encodedPassword);
			userRepository.save(user);
		} else {
			throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
		}
	}

	/**
	 * 닉네임 변경
	 */
	@Transactional
	public String updateNickname(String newNickname) {
		String userEmail = getMyUserWithAuthorities().getEmail();
		User user = userRepository.findOneWithAuthoritiesByEmail(userEmail)
			.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

		user.setNickname(newNickname);
		userRepository.save(user);

		return user.getNickname();
	}

}
