package com.ssafy.ssap.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ssap.domain.alarm.Alarm;
import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.dto.alarm.AlarmCreateDto;
import com.ssafy.ssap.repository.AlarmRepository;
import com.ssafy.ssap.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {
	private final AlarmRepository alarmRepository;
	private final UserRepository userRepository;

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
}
