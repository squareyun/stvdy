package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.studyroom.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    void deleteByRoomId(Integer roomId);

    Long countByRoomIdAndIsOut(Integer roomId, boolean isOut);
}