package com.ssafy.ssap.dto.alarm;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class AlarmDetailResponseDto {
	private Integer id;
	private String title;
	private String detail;
	// private String imagePath;
	private Boolean isRead;
	private LocalDateTime registTime;
	private Integer userNo;
}
