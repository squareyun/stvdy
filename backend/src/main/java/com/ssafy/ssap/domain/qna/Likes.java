package com.ssafy.ssap.domain.qna;

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
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "is_good", columnDefinition = "bit(1)")
    private Boolean isGood;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "question_id", unique = false)
    @Nullable
    private Question question;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "answer_id", unique = false)
    @Nullable
    private Answer answer;
}
