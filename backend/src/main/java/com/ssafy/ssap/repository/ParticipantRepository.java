package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.studyroom.Participant;
import com.ssafy.ssap.domain.studyroom.ParticipantRoleNs;
import com.ssafy.ssap.domain.studyroom.Room;
import com.ssafy.ssap.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    @Deprecated
    void deleteByRoomId(Integer roomId);

    Integer countByRoomIdAndIsOut(Integer roomId, boolean isOut);

    List<Participant> findAllByRoomId(Integer roomId);

    Optional<Participant> findByRoomIdAndUserId(Integer roomId, Integer userId);

    @Query("SELECT p.user FROM Participant p where p.room=:room and p.role = :role")
    User findUserByRoomIdAndRole(Room room, ParticipantRoleNs role);
}