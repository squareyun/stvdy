package com.ssafy.ssap.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.dto.alarm.AlarmCreateDto;
import com.ssafy.ssap.dto.alarm.AlarmListResponseDto;
import com.ssafy.ssap.service.AlarmService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/alarms")
@RequiredArgsConstructor
public class AlarmController {

	private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);
	private final AlarmService alarmService;

	/**
	 * alarm 생성
	 */
	@PostMapping("/add")
	public ResponseEntity<Map<String, Object>> add(@RequestBody AlarmCreateDto alarmCreateDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			Integer alarmId = alarmService.create(alarmCreateDto);
			logger.info("{} alarm 생성 성공", alarmId);
			resultMap.put("message", MessageFormat.SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("alarm 생성 실패: {}", e.getMessage());
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getClass().getSimpleName());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/**
	 * alarm 리스트 조회 - 최신순 정렬
	 */
	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getAlarmList() {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			List<AlarmListResponseDto> alarmList = alarmService.getAlarmList();
			logger.info("{} alarm 목록 조회 성공", alarmList.size());
			resultMap.put("message", MessageFormat.SUCCESS);
			resultMap.put("alarmList", alarmList);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("alarm 목록 조회 실패: {}", e.getMessage());
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getClass().getSimpleName());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
