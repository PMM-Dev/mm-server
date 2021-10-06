package com.kwon770.mm.domain.report;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kwon770.mm.domain.BaseTimeEntity;
import com.kwon770.mm.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Member author;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer likeCount = 0;

    @Builder
    public Report(Member author, String content) {
        this.author = author;
        this.content = content;
    }
}
