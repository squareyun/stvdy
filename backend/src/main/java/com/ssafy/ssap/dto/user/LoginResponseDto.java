package com.ssafy.ssap.dto.user;

import com.ssafy.ssap.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

	private Integer id;
	private String email;
	private String name;
	private String nickname;

	public static LoginResponseDto from(User user) {
		if (user == null)
			return null;

		return LoginResponseDto.builder()
			.id(user.getId())
			.email(user.getEmail())
			.name(user.getName())
			.nickname(user.getNickname())
			.build();
	}
}
