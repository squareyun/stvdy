package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.studyroom.ParticipantsRoleNs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantsRoleNsRepository extends JpaRepository<ParticipantsRoleNs, String> {

    public ParticipantsRoleNs findByName(String name);
}
