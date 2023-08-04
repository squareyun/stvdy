package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.studyroom.RoomLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomLogRepository extends JpaRepository<RoomLog, Integer> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE room_log SET spend_hour = TIME_FORMAT(SEC_TO_TIME(TIMESTAMPDIFF(SECOND, enter_time, CURRENT_TIMESTAMP)), '%H:%i:%s') WHERE room_id = :roomId", nativeQuery = true)
    void updateSpendHourByAllRoomId(@Param("roomId") Integer roomId);

    Optional<RoomLog> findByRoomIdAndUserId(Integer roomNo, Integer userNo);
}
