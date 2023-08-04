package com.ssafy.ssap.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.domain.user.UserStateNs;
import com.ssafy.ssap.repository.UserRepository;
import com.ssafy.ssap.repository.UserStateNsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchedulerService {

	private final UserRepository userRepository;
	private final UserStateNsRepository userStateNsRepository;

	/*
	 * 회원탈퇴 30일 후 계정 정지
	 */
	@Scheduled(cron = "0 35 9 * * *")
	public void processWithdrawal() {
		UserStateNs withdrawnState = userStateNsRepository.findByName("탈퇴");
		List<User> users = userRepository.findByStateAndActivated(withdrawnState, true);

		for (User user : users) {
			System.out.println(user.toString());
			LocalDateTime withdrawalDate = user.getStateTime();
			LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
			// LocalDateTime thirtyDaysAgo = LocalDateTime.now();
			if (withdrawalDate.isBefore(thirtyDaysAgo)) {
				user.setActivated(false);
				userRepository.save(user);
			}
		}
	}

	/*
	 * 계정 정지 30일 후 해제
	 */
	// @Scheduled(cron = "0 0 0 * * *")
	// public void releaseAccountSuspension() {
	// 	List<User> users = userRepository.findByState("정지");
	// 	for (User user : users) {
	// 		LocalDateTime withdrawalDate = user.getStateTime();
	// 		LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
	// 		if (withdrawalDate.isBefore(thirtyDaysAgo)) {
	// 			user.setState(new UserStateNs("사용자"));
	// 			user.setStateTime(null);
	// 			userRepository.save(user);
	// 		}
	// 	}
	// }
}
