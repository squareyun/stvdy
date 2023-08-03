package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.studyroom.ParticipantRoleNs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRoleNsRepository extends JpaRepository<ParticipantRoleNs, String> {

    public ParticipantRoleNs findByName(String name);
}
