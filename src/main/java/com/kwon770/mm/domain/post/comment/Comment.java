package com.kwon770.mm.domain.post.comment;

import com.kwon770.mm.domain.BaseTimeEntity;
import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.post.Post;
import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.util.SecurityUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;

    @ManyToMany(mappedBy = "likedComments")
    List<Member> likingMembers = new ArrayList<>();

    @Builder
    public Comment(String content, Post post, Member author) {
        this.content = content;
        this.post = post;
        this.author = author;
    }

    public boolean getDidLike(Long memberId) {
        for (final Member likedMember : likingMembers) {
            if (memberId.equals(likedMember.getId())) {
                return true;
            }
        }

        return false;
    }
}
