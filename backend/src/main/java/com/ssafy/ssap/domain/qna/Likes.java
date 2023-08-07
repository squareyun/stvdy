package com.ssafy.ssap.domain.qna;

import com.ssafy.ssap.domain.user.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "is_good", columnDefinition = "bit(1)")
    private Boolean isGood;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "question_id", unique = false)
    @Nullable
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Question question;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "answer_id", unique = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Nullable
    private Answer answer;
}
