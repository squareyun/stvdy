package com.ssafy.ssap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.ssap.domain.qna.Likes;

public interface LikesRepository extends JpaRepository<Likes, Integer> {
}
