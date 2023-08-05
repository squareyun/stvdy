package com.ssafy.ssap.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.domain.user.UserStateNs;

public interface UserRepository extends JpaRepository<User, Integer> {

	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneWithAuthoritiesByEmail(String email);

	User findByEmail(String email);

	Optional<User> findByEmailAndName(String email, String name);

	List<User> findByStateAndActivated(UserStateNs state, boolean activated);

}
