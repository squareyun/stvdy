package com.ssafy.ssap.repository;

import com.ssafy.ssap.domain.qna.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleImageRepository  extends JpaRepository<ArticleImage, Integer> {

    List<ArticleImage> findAllByQuestionId(Integer id);

    List<ArticleImage> findAllByAnswerId(Integer id);

    void deleteByQuestionId(Integer id);

    void deleteByAnswerId(Integer id);
}
