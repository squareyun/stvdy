package com.ssafy.ssap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssap.domain.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneWithAuthoritiesByEmail(String email);

	User findByEmail(String email);

}
