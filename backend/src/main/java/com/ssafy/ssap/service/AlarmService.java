package com.ssafy.ssap.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ssap.domain.alarm.Alarm;
import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.dto.alarm.AlarmCreateDto;
import com.ssafy.ssap.dto.alarm.AlarmDetailResponseDto;
import com.ssafy.ssap.dto.alarm.AlarmListResponseDto;
import com.ssafy.ssap.repository.AlarmRepository;
import com.ssafy.ssap.repository.UserRepository;
import com.ssafy.ssap.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {
	private final AlarmRepository alarmRepository;
	private final UserRepository userRepository;
	private final UserService userService;

	@Transactional
	public Integer create(AlarmCreateDto alarmCreateDto) throws Exception {
		User user = userRepository.getReferenceById(alarmCreateDto.getUserNo());

		Alarm alarm = Alarm.builder()
			.title(alarmCreateDto.getTitle())
			.detail(alarmCreateDto.getDetail())
			.isRead(false)
			.linkedUrl("/alarmdetail/")
			.registTime(LocalDateTime.now())
			.user(user)
			.build();

		alarmRepository.save(alarm);

		String urlAddress = "/alarmdetail/" + alarm.getId();
		Alarm tempAlarm = alarmRepository.findById(alarm.getId())
			.orElseThrow(() -> new IllegalArgumentException("알람 정보를 찾을 수 없습니다."));
		tempAlarm.setLinkedUrl(urlAddress);

		alarmRepository.save(tempAlarm);

		return alarm.getId();
	}

	public List<AlarmListResponseDto> getAlarmList() {
		String userEmail = userService.getMyUserWithAuthorities().getEmail();
		User user = userRepository.findOneWithAuthoritiesByEmail(userEmail)
			.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

		List<Integer> targetUserIds = List.of(1, 2, user.getId());
		List<Alarm> alarmList = alarmRepository.findByUserIdInOrderByRegistTimeDesc(targetUserIds);

		return alarmList.stream()
			.map(this::mapToDto)
			.collect(Collectors.toList());
	}

	public List<AlarmListResponseDto> getRecentAlarms() {
		String userEmail = userService.getMyUserWithAuthorities().getEmail();
		User user = userRepository.findOneWithAuthoritiesByEmail(userEmail)
			.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

		List<Integer> targetUserIds = List.of(1, 2, user.getId());

		LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

		Sort sort = Sort.by(Sort.Direction.DESC, "registTime");
		PageRequest pageRequest = PageRequest.of(0, 20, sort);

		List<Alarm> alarms = alarmRepository.findByUserIdInAndRegistTimeAfterOrderByRegistTimeDesc(targetUserIds,
			oneMonthAgo, pageRequest);

		return alarms.stream()
			.map(this::mapToDto)
			.collect(Collectors.toList());
	}

	private AlarmListResponseDto mapToDto(Alarm alarm) {
		return new AlarmListResponseDto(
			alarm.getId(),
			alarm.getTitle(),
			alarm.getDetail(),
			alarm.getIsRead(),
			alarm.getLinkedUrl(),
			alarm.getRegistTime(),
			alarm.getUser().getId()
		);
	}

	public long countUnReadAlarms(List<AlarmListResponseDto> alarmList) {
		return alarmList.stream()
			.filter(alarm -> !alarm.getIsRead() && alarm.getUserNo() != 1 && alarm.getUserNo() != 2)
			.count();
	}

	@Transactional
	public AlarmDetailResponseDto getAlarmDetail(Integer alarmId) {

		String userEmail = SecurityUtil.getCurrentUsername()
			.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));
		User user = userRepository.findOneWithAuthoritiesByEmail(userEmail)
			.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

		Alarm alarm = alarmRepository.findById(alarmId)
			.orElseThrow(() -> new IllegalArgumentException("알림 정보를 찾을 수 없습니다."));

		Integer alarmUserNo = alarm.getUser().getId();
		Boolean flag = false;
		if (alarmUserNo == 1 || alarmUserNo == 2) {
			flag = false;
		} else if (alarmUserNo != user.getId()) {
			new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
		} else {
			flag = true;
		}

		AlarmDetailResponseDto alarmDetail = AlarmDetailResponseDto.builder()
			.id(alarm.getId())
			.title(alarm.getTitle())
			.detail(alarm.getDetail())
			.isRead(flag)
			// .linkedUrl(alarm.getLinkedUrl())
			.registTime(alarm.getRegistTime())
			.userNo(user.getId())
			.build();

		alarm.setIsRead(flag);
		alarmRepository.save(alarm);

		return alarmDetail;
	}

}
