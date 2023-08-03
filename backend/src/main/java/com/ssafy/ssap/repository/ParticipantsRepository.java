package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.studyroom.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantsRepository extends JpaRepository<Participants, Integer> {
    void deleteByRoomId(Integer roomId);

    Long countByRoomIdAndIsOut(Integer roomId, boolean isOut);

    Optional<Participants> findByUser_idAndRoom_id(Integer user_id, Integer room_id);
}