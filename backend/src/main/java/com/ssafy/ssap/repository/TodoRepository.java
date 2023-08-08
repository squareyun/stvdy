package com.ssafy.ssap.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssap.domain.todo.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

	List<Todo> findByRegistTimeBetweenAndUser_Id(LocalDateTime startTime, LocalDateTime endTime, Integer userId);
}
