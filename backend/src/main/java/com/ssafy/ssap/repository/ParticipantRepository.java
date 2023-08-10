package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.studyroom.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    void deleteByRoomId(Integer roomId);

    Integer countByRoomIdAndIsOut(Integer roomId, boolean isOut);

    List<Participant> findAllByRoomId(Integer roomId);

    Optional<Participant> findByRoomIdAndUserId(Integer roomId, Integer userId);
}