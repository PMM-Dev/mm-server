package com.kwon770.mm.domain.feedback;

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
public class Feedback extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;

    @Column(nullable = false)
    private String content;

    @ManyToMany(mappedBy = "likedFeedbacks")
    @JsonBackReference
    private List<Member> likingMembers = new ArrayList<>();

    @Builder
    public Feedback(Member author, String content) {
        this.author = author;
        this.content = content;
    }

    public void removeAllMemberConnection() {
        for (Member member : likingMembers) {
            member.subtractedLikedFeedback(this);
        }
    }
}
