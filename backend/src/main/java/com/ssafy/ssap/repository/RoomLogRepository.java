package com.ssafy.ssap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.ssap.domain.studyroom.Participants;
import com.ssafy.ssap.domain.studyroom.RoomLog;

@Repository
public interface RoomLogRepository extends JpaRepository<RoomLog, Long> {
}
