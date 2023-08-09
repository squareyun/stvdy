package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    String findImagePathById(Integer id);


}
