package com.ssafy.ssap.dto.alarm;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AlarmListResponseDto {
	private Integer id;
	private String title;
	private String detail;
	private Boolean isRead;
	private LocalDateTime registTime;
	private Integer userNo;
}
