package com.ssafy.ssap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssap.domain.todo.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
