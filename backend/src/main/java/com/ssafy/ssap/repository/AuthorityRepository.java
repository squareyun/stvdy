package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.user.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
