package com.ssafy.ssap.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RedisUtil {
	private final StringRedisTemplate redisAuthNumTemplate;
	private static final long EXPIRATION_TIME = 5; // 인증번호의 유효 시간(단위: 분)

	/**
	 * 이메일 인증번호
	 * key: userEmail
	 * value: authNum
	 */
	public boolean isAuthNumValid(String email, String enteredAuthNumber) {
		String storedAuthNumber = getData(email);
		return storedAuthNumber != null && storedAuthNumber.equals(enteredAuthNumber);
	}

	public void setDataExpire(String key, String authNumber) {
		ValueOperations<String, String> valueOperations = redisAuthNumTemplate.opsForValue();
		valueOperations.set(key, authNumber, EXPIRATION_TIME, TimeUnit.MINUTES);
	}

	public String getData(String key) {
		ValueOperations<String, String> valueOperations = redisAuthNumTemplate.opsForValue();
		return valueOperations.get(key);
	}

	public boolean existData(String key) {
		return Boolean.TRUE.equals(redisAuthNumTemplate.hasKey(key));
	}

	public void deleteData(String key) {
		redisAuthNumTemplate.delete(key);
	}
}
