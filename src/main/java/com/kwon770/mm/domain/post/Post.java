package com.kwon770.mm.domain.post;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.web.dto.post.PostRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Entity
public class Post {

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

    @ManyToMany(mappedBy = "likedPosts")
    List<Member> likingMembers = new ArrayList<>();

    @Builder
    public Post(String title, String content, Member author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(PostRequestDto postRequestDto, List<PostImage> newPostImages) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.postImages.addAll(newPostImages);
    }

    public List<MultipartFile> getAddedPostImages(List<MultipartFile> newImages) {
        return newImages.stream().filter(image -> {
            for (PostImage postImage : postImages) {
                if (postImage.getOriginalFileName().equals(image.getOriginalFilename())) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }

    public List<PostImage> getRemovedPostImages(List<MultipartFile> newImages) {
        return postImages.stream().filter(image -> {
            for (MultipartFile newImage : newImages) {
                if (newImage.getOriginalFilename().equals(image.getOriginalFileName())) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }
}
