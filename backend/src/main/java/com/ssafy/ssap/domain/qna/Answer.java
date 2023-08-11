package com.ssafy.ssap.domain.qna;

import com.ssafy.ssap.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(columnDefinition = "text")
    private String detail;

    @NotNull
    @CreationTimestamp
    @Column(name = "regist_time", columnDefinition = "timestamp")
    private LocalDateTime registTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Question question;

    @Formula("(SELECT coalesce(SUM(case when l.is_good = 1 then 1 else -1 end), 0) FROM likes l WHERE l.answer_id = id)")
    private Integer answerScore;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "answer")
    List<Likes> likes;

    @OneToMany(mappedBy = "answer")
    List<ArticleImage> articleImage;

    public void addQuestion(Question question) {
        this.question = question;
//        question.answerList.add(this);

    }

    public Integer update(String content) {
        this.detail = content;
        return id;
    }
}
