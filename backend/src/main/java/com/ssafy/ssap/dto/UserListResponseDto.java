package com.ssafy.ssap.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserListResponseDto {

	private Integer id;
	private String name;
	private String nickname;
	private String profile;
	private LocalDateTime registTime;

}
