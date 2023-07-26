package com.ssafy.ssap.domain.qna;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "best_answer")
    @Nullable
    private Answer answer;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category")
    private QuestionCategoryNs category;
}
