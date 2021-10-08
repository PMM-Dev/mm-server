package com.kwon770.mm.domain.report;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kwon770.mm.domain.BaseTimeEntity;
import com.kwon770.mm.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member author;

    @Column(nullable = false)
    private String content;

    @ManyToMany(mappedBy = "likedReports")
    @JsonBackReference
    private List<Member> likingMembers = new ArrayList<>();

    @Builder
    public Report(Member author, String content) {
        this.author = author;
        this.content = content;
    }

    public void removeAllMemberConnection() {
        for (Member member : likingMembers) {
            member.subtractedLikedReport(this);
        }
    }
}
