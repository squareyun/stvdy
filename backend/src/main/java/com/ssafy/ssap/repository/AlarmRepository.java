package com.ssafy.ssap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.ssap.domain.alarm.Alarm;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
}
