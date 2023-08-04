package com.ssafy.ssap.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ssap.domain.studyroom.Participant;
import com.ssafy.ssap.domain.studyroom.Room;
import com.ssafy.ssap.domain.studyroom.RoomLog;
import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.dto.RoomCreateDto;
import com.ssafy.ssap.repository.ParticipantRepository;
import com.ssafy.ssap.repository.ParticipantRoleNsRepository;
import com.ssafy.ssap.repository.RoomLogRepository;
import com.ssafy.ssap.repository.RoomRepository;
import com.ssafy.ssap.repository.UserRepository;

import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
	private final Logger logger = LoggerFactory.getLogger(RoomService.class);
	private final RoomRepository roomRepository;
	private final ParticipantRepository participantRepository;
	private final ParticipantRoleNsRepository participantRoleNsRepository;
	private final RoomLogRepository roomLogRepository;
	private final UserRepository userRepository;

	/**
	 * OpenVidu variables
	 */
	private OpenVidu openVidu;
	private final String OPENVIDU_URL = "http://localhost:4443/";
	private final String SECRET = "MY_SECRET";

	/**
	 * 스터디룸 생성
	 */
	@Transactional
	public Integer create(RoomCreateDto roomCreateDto) {
		// 방 추가
		Room room = Room.builder()
			.title(roomCreateDto.getTitle())
			.quota(roomCreateDto.getQuota())
			.isPrivacy(roomCreateDto.getIsPrivacy())
			.isValid(true)
			.password(roomCreateDto.getPassword())
			.endTime(
				LocalDateTime.now().plusHours(roomCreateDto.getEndHour()).plusMinutes(roomCreateDto.getEndMinute()))
			.imagePath(roomCreateDto.getImagePath())
			.rule(roomCreateDto.getRule())
			.build();
		roomRepository.save(room);

		addParticipant(room, "호스트");
		addRoomLog(room, roomCreateDto.getUserNo());

		return room.getId();
	}

	public void addParticipant(Room room, String role) {
		// 참여자 추가 (방장)
		Participant participant = Participant.builder()
			.isOut(false)
			.role(participantRoleNsRepository.findByName(role))
			.room(room)
			.build();
		participantRepository.save(participant);
	}

	public void addParticipant(Integer roomNo, String role) {
		Room room = roomRepository.findById(roomNo).orElse(null);
		addParticipant(room, role);
	}

	public void addRoomLog(Room room, Integer userId) {
		User user = userRepository.findById(userId).orElse(null);
		// 접속 기록 추가
		RoomLog roomLog = RoomLog.builder()
			.roomTitle(room.getTitle())
			.enterTime(LocalDateTime.now())
			.room(room)
			.user(user)
			.build();
		roomLogRepository.save(roomLog);
	}

	public void addRoomLog(Integer roomNo, Integer userId) {
		Room room = roomRepository.findById(roomNo).orElse(null);
		if (room == null)
			logger.error(roomNo + "에 해당하는 방이 DB에 존재하지 않음");
		addRoomLog(room, userId);
	}

	/**
	 * 스터디룸 폐쇄
	 */
	@Transactional
	public void close(Integer roomNo) {
		// 방에 접속한 사람들의 room_log 데이터 업데이트
		roomLogRepository.updateSpendHourByAllRoomId(roomNo);
		participantRepository.deleteByRoomId(roomNo);

		roomRepository.setValidToZeroByRoomId(roomNo);
	}

	public String makeSession() {
		//session 생성, connection 생성, 토큰 생성
		Session session;
		String token;
		try {
			openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
			session = this.openVidu.createSession(); //오픈비두 서버에 세션 생성
			token = joinSession(session.getSessionId()); //joinSession에서 connection 생성
			return token;
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			throw new RuntimeException(e);
		}
	}

	public String getSessionIdByRoomNo(Integer roomNo) {
		Room room = roomRepository.findById(roomNo).orElse(null);
		if (room == null)
			logger.error(roomNo + "에 해당하는 방이 DB에 존재하지 않음");
		return room.getSessionId();
	}

	public String joinSession(String sessionId) {
		//이미 열려있는 세션에 참가하고 토큰 반환
		String token = null;

		//입장 요청한 session이 존재하는지 확인 (무조건 존재해야함!)
		Session session = openVidu.getActiveSession(sessionId);
		if (session == null) {
			logger.error("session Id not matching");
			return null;
		}

		//커넥션 생성 및 토큰 반환
		try {
			ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
				.build();
			token = session.createConnection(connectionProperties).getToken();
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			logger.error("can't build connection properties or get token from it");
		}

		return token;
	}

	public Integer findRoomId(Integer roomcode) {
		return 0;
	}

	public boolean checkValid(Integer roomNo, String password) throws RuntimeException {
		//방 입장이 가능한지 확인하는 메소드

		Room room = roomRepository.findById(roomNo).orElse(null);
		if (room == null) {
			throw new RuntimeException("ERROR: can't find room matching roomNo");
		}

		if (participantRepository.countByRoomIdAndIsOut(roomNo, false) >= room.getQuota()) {
			//check quota
			logger.trace(roomNo + " room is full");
			return false;
		} else {
			logger.trace(roomNo + " room is not full");
		}
		if (!room.getPassword().isBlank() && !password.isBlank()) {
			logger.trace("roomPassword, 입력password 둘 다 존재");
			//check password
			if (!room.getPassword().equals(password)) {
				logger.trace("password 불일치" + room.getPassword() + " / " + password);
				return false;
			} else {
				logger.trace("password 일치");
			}
		}
		return true;
	}

}

