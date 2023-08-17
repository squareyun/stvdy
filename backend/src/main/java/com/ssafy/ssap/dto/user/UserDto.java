package com.ssafy.ssap.dto.user;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserDto {

	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	private String name;
	private String nickname;
	private String state;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String authNum;

	private Set<AuthorityDto> authorityDtoSet;

	public static UserDto from(User user) {
		if (user == null)
			return null;

		return UserDto.builder()
			.email(user.getEmail())
			.name(user.getName())
			.nickname(user.getNickname())
			.state(user.getState().getName())
			.authorityDtoSet(user.getAuthorities().stream()
				.map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
				.collect(Collectors.toSet()))
			.build();
	}
}
