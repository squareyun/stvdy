package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.qna.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleImageRepository  extends JpaRepository<ArticleImage, Integer> {
}
