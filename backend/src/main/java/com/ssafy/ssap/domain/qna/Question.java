package com.ssafy.ssap.domain.qna;

import static jakarta.persistence.FetchType.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.ssap.domain.user.User;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	@Column(length = 20)
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

	@OneToOne(mappedBy = "question")
	ArticleImage articleImage;

	@OneToOne(mappedBy = "question")
	Likes likes;

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
