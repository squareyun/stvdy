package com.ssafy.ssap.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<Map<String, Object>> getAlarms() {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			long unreadCnt = 0;
			List<AlarmListResponseDto> alarms = alarmService.getRecentAlarms();
			if (alarms.size() > 0) {
				unreadCnt = alarmService.countUnReadAlarms(alarms);
			}
			logger.info("{} alarm 한달 이내 목록 조회 성공", alarms.size());
			resultMap.put("message", MessageFormat.SUCCESS);
			resultMap.put("alarms", alarms);
			resultMap.put("unreadCnt", unreadCnt);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("alarm 한달 이내 목록 조회 실패: {}", e.getMessage());
			resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getClass().getSimpleName());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/**
	 * alarm 상세 페이지 이동
	 */
	@GetMapping("/{alarmNo}")
	public String redirectToAlarmDetail(@PathVariable Integer alarmNo) {
		String questionDetailUrl = null;
		try {
			questionDetailUrl = alarmService.getAlarmDetail(alarmNo).getDetail();
			logger.info("{} alarm redirect 성공", questionDetailUrl);
		} catch (Exception e) {
			logger.error("alarm redirect 실패: {}", e.getMessage());
		}

		return "redirect:" + questionDetailUrl;
	}

	/**
	 * alarm 리스트 조회 - 최신순 정렬
	 *
	 @GetMapping("/list") public ResponseEntity<Map<String, Object>> getAlarmList() {
	 Map<String, Object> resultMap = new HashMap<>();
	 HttpStatus status = null;
	 try {
	 long unreadCnt = 0;
	 List<AlarmListResponseDto> alarmList = alarmService.getAlarmList();
	 if (alarmList.size() > 0) {
	 unreadCnt = alarmService.countUnReadAlarms(alarmList);
	 }
	 logger.info("{} alarm 목록 조회 성공", alarmList.size());
	 resultMap.put("message", MessageFormat.SUCCESS);
	 resultMap.put("alarms", alarmList);
	 resultMap.put("unreadCnt", unreadCnt);
	 status = HttpStatus.ACCEPTED;
	 } catch (Exception e) {
	 logger.error("alarm 목록 조회 실패: {}", e.getMessage());
	 resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getClass().getSimpleName());
	 status = HttpStatus.INTERNAL_SERVER_ERROR;
	 }

	 return new ResponseEntity<Map<String, Object>>(resultMap, status);
	 }
	 */

	/**
	 * alarm 상세 조회
	 *
	 @GetMapping("/detail/{alarmNo}") public ResponseEntity<Map<String, Object>> getAlarmDetail(@PathVariable Integer alarmNo) {
	 Map<String, Object> resultMap = new HashMap<>();
	 HttpStatus status = null;
	 try {
	 long unreadCnt = 0;
	 AlarmDetailResponseDto alarmDetailResponseDto = alarmService.getAlarmDetail(alarmNo);

	 logger.info("{} alarm 상세 조회 성공", alarmDetailResponseDto.getId());
	 resultMap.put("message", MessageFormat.SUCCESS);
	 resultMap.put("alarmDetail", alarmDetailResponseDto);
	 status = HttpStatus.ACCEPTED;
	 } catch (Exception e) {
	 logger.error("alarm 상세 조회 실패: {}", e.getMessage());
	 resultMap.put("message", MessageFormat.SERVER_FAIL + ": " + e.getClass().getSimpleName());
	 status = HttpStatus.INTERNAL_SERVER_ERROR;
	 }

	 return new ResponseEntity<Map<String, Object>>(resultMap, status);
	 }
	 */
}
