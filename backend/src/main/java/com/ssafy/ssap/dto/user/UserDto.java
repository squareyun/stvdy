package com.ssafy.ssap.dto.user;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.ssap.domain.user.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

	@NotNull
	@Size(min = 3, max = 50)
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull
	@Size(min = 3, max = 100)
	private String password;

	@NotNull
	@Size(min = 3, max = 50)
	private String name;

	@NotNull
	@Size(min = 3, max = 50)
	private String nickname;

	private Set<AuthorityDto> authorityDtoSet;

	public static UserDto from(User user) {
		if (user == null)
			return null;

		return UserDto.builder()
			.email(user.getEmail())
			.name(user.getName())
			.nickname(user.getNickname())
			.authorityDtoSet(user.getAuthorities().stream()
				.map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
				.collect(Collectors.toSet()))
			.build();
	}
}
