package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.studyroom.Room;
import com.ssafy.ssap.dto.RoomDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Modifying
    @Query("UPDATE Room r SET r.isValid = false WHERE r.id = :roomId")
    void setValidToZeroByRoomId(@Param("roomId") Integer roomId);

//@Entity(name = )
    @Modifying
    @Query("SELECT new com.ssafy.ssap.dto.RoomDto(r.id, r.title, r.quota, r.isPrivacy, r.isValid, r.sessionId, r.password, r.endTime, r.imagePath, r.rule) FROM Room r")
    List<RoomDto> findAllRooms();


}
