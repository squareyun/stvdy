package com.ssafy.ssap.domain.qna;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.ssap.domain.user.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(length = 50)
    private String title;

    @NotNull
    @Column(columnDefinition = "text")
    private String detail;

    @NotNull
    @CreationTimestamp
    @Column(name = "regist_time", columnDefinition = "timestamp")
    private LocalDateTime registTime;

    private int hit;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "best_answer")
    @Nullable
    private Answer answer;

    @NotNull
    @JsonIgnore
    private String category;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "question")
    List<Answer> answerList = new ArrayList<>();

	@OneToMany(mappedBy = "question")
	List<ArticleImage> articleImage;

    @OneToMany(mappedBy = "question")
    List<Likes> likes;

    public Integer update(String title, String detail, String category) {
        this.title = title;
        this.detail = detail;
        this.category = category;
        return id;
    }

    public void selectAnswer(Answer answer) {
        this.answer = answer;
    }

    public void updateHit() {
        this.hit++;
    }
}
