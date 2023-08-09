package com.ssafy.ssap.domain.qna;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ArticleImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "blob")
    @Lob
    private String path;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "question_id")
    @Nullable
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Question question;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "answer_id")
    @Nullable
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Answer answer;
}
