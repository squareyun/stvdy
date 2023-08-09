package com.ssafy.ssap.dto.alarm;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlarmCreateDto {
	private String title;
	private String detail;
	// private String imagePath;
	private Integer userNo;
}
