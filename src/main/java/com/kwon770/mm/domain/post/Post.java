package com.kwon770.mm.domain.post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kwon770.mm.domain.BaseTimeEntity;
import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.post.comment.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @OneToMany(mappedBy = "post", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PostImage> postImages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;

    private int viewCount = 0;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "likedPosts", cascade = {CascadeType.ALL})
    @JsonBackReference
    private List<Member> likingMembers = new ArrayList<>();


    @Builder
    public Post(String title, String content, Member author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getPostImagesCount() {
        return postImages.size();
    }

    public boolean getIsExistImages() {
        if (postImages.isEmpty()) {
            return false;
        }

        return true;
    }

    public boolean getDidLike(Long memberId) {
        for (final Member likedMember : likingMembers) {
            if (memberId.equals(likedMember.getId())) {
                return true;
            }
        }

        return false;
    }

    public void increaseViewCount() {
        this.viewCount += 1;
    }
}
