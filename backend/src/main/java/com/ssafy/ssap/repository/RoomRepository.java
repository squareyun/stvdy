package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.studyroom.Room;
import com.ssafy.ssap.dto.RoomDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer>, JpaSpecificationExecutor<Room> {

    @Modifying
    @Query("UPDATE Room r SET r.isValid = false WHERE r.id = :roomId")
    void setValidToZeroByRoomId(@Param("roomId") Integer roomId);

//@Entity(name = )
    @Modifying
    @Query("SELECT new com.ssafy.ssap.dto.RoomDto(r.id, r.title, r.quota, r.isPrivacy, r.isValid, r.sessionId, r.password, r.endTime, r.rule) FROM Room r WHERE r.isValid = true")
    List<RoomDto> findAllValidRooms();

    Optional<Room> findByCode(String code);
}
