package com.ssafy.ssap.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ssap.domain.alarm.Alarm;
import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.dto.alarm.AlarmCreateDto;
import com.ssafy.ssap.dto.alarm.AlarmListResponseDto;
import com.ssafy.ssap.repository.AlarmRepository;
import com.ssafy.ssap.repository.UserRepository;

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
			.registTime(LocalDateTime.now())
			.user(user)
			.build();

		alarmRepository.save(alarm);

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

	private AlarmListResponseDto mapToDto(Alarm alarm) {
		return new AlarmListResponseDto(
			alarm.getId(),
			alarm.getTitle(),
			alarm.getDetail(),
			alarm.getIsRead(),
			alarm.getRegistTime(),
			alarm.getUser().getId()
		);
	}

}
