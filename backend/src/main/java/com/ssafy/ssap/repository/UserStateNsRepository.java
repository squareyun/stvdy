package com.ssafy.ssap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.ssap.domain.user.UserStateNs;

@Repository
public interface UserStateNsRepository extends JpaRepository<UserStateNs, Integer> {
	UserStateNs findByName(String name);
}