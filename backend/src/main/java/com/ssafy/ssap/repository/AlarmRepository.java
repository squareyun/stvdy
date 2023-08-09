package com.ssafy.ssap.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.ssap.domain.alarm.Alarm;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

	List<Alarm> findByUserIdInAndRegistTimeAfterOrderByRegistTimeDesc(List<Integer> userIds, LocalDateTime dateTime,
		Pageable pageable);

	List<Alarm> findByUserIdInOrderByRegistTimeDesc(List<Integer> userIds);
}
