package com.ssafy.ssap.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ssafy.ssap.repository.UserRepository;
import com.ssafy.ssap.util.RedisUtil;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	private final UserRepository userRepository;
	private final RedisUtil redisUtil;

	public int makeRandomNumber() {
		Random random = new Random();
		int checkNum = random.nextInt(888888) + 111111;
		return checkNum;
	}

	public boolean sendEmail(String email) throws MessagingException {

		if (userRepository.findOneWithAuthoritiesByEmail(email).orElse(null) != null) {
			System.out.println("이미 존재하는 사용자입니다.");
			return false;
			// throw new RuntimeException(email + " -> 활성화되어 있지 않습니다.");
		}

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

		// 난수 생성
		int authNum = makeRandomNumber();

		String message = "홈페이지를 방문해주셔서 감사합니다." +
			"<br><br>" +
			"인증번호는 " + authNum + " 입니다." +
			"<br><br>" +
			"해당 인증번호를 인증번호 확인란에 기입하여 주시기바랍니다.";

		helper.setSubject("[SSAP] 회원가입을 위한 인증메일입니다.");
		helper.setTo(email);

		// true 입력시 html 양식으로 전달됨. 안하면 일단 텍스트 형식임.
		helper.setText(message, true);

		// Redis에 인증번호 저장
		String key = "user_email:" + email;
		redisUtil.setDataExpire(key, String.valueOf(authNum));
		javaMailSender.send(mimeMessage);

		return true;
	}
}
