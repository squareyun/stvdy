package com.ssafy.ssap.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TodoResponseDto {

	private Integer id;
	private Boolean doneFlag;
	private String objective;
	private LocalDateTime registTime;
	private Integer userId;

}
