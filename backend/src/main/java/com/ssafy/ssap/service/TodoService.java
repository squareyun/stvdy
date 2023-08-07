package com.ssafy.ssap.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ssap.domain.todo.Todo;
import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.dto.TodoResponseDto;
import com.ssafy.ssap.repository.TodoRepository;
import com.ssafy.ssap.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {

	private final TodoRepository todoRepository;
	private final UserService userService;
	private final UserRepository userRepository;

	/**
	 * todo 생성
	 */
	@Transactional
	public Integer create(String objective) throws Exception {
		String userEmail = userService.getMyUserWithAuthorities().getEmail();
		User user = userRepository.findOneWithAuthoritiesByEmail(userEmail)
			.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

		Todo todo = Todo.builder()
			.objective(objective)
			.doneFlag(false)
			.registTime(LocalDateTime.now())
			.user(user)
			.build();

		todoRepository.save(todo);

		return todo.getId();
	}

	/**
	 * todo 삭제
	 */
	@Transactional
	public void delete(Integer todoId) {
		String userEmail = userService.getMyUserWithAuthorities().getEmail();
		User user = userRepository.findOneWithAuthoritiesByEmail(userEmail)
			.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

		Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("todo를 찾을 수 없습니다."));

		if (todo.getUser().getId() != user.getId()) {
			new IllegalArgumentException("사용자 정보가 일치하지 않습니다.");
		}

		todoRepository.deleteById(todoId);
	}

	/**
	 * todo 완료 표시: done_flag toggle
	 */
	@Transactional
	public void updateDoneFlag(Integer todoId) {
		String userEmail = userService.getMyUserWithAuthorities().getEmail();
		User user = userRepository.findOneWithAuthoritiesByEmail(userEmail)
			.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));
		Todo todo = todoRepository.findById(todoId)
			.orElseThrow(() -> new IllegalArgumentException("todo 정보를 찾을 수 없습니다."));
		;
		Boolean flag = todo.getDoneFlag();

		if (user.getId() != todo.getUser().getId()) {
			new IllegalArgumentException("사용자 정보가 일치하지 않습니다.");
		}

		todo.setDoneFlag(!flag);
		todoRepository.save(todo);
	}

	/**
	 * todo 목록 - 일별 조회
	 */
	public List<TodoResponseDto> getDailyTodoList(LocalDate date) {
		String userEmail = userService.getMyUserWithAuthorities().getEmail();
		User user = userRepository.findOneWithAuthoritiesByEmail(userEmail)
			.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

		LocalDateTime startOfDay = date.atStartOfDay();
		LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

		List<Todo> todos = todoRepository.findByRegistTimeBetweenAndUser_Id(startOfDay, endOfDay, user.getId());

		return todos.stream()
			.map(this::convertToResponseDto)
			.collect(Collectors.toList());
	}

	private TodoResponseDto convertToResponseDto(Todo todo) {
		return TodoResponseDto.builder()
			.id(todo.getId())
			.doneFlag(todo.getDoneFlag())
			.objective(todo.getObjective())
			.registTime(todo.getRegistTime())
			.userId(todo.getUser().getId())
			.build();
	}
}
