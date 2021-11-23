package com.kwon770.mm.dto.post;

import com.kwon770.mm.domain.post.Post;
import com.kwon770.mm.util.CommonUtil;
import com.kwon770.mm.util.SecurityUtil;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostInfoDto {

    private Long id;
    private String title;
    private String content;
    private String authorName;
    private String authorEmail;
    private String createDate;
    private int viewCount;
    private int likeCount;
    private boolean didLike;
    private boolean isExistImage;
    private int imagesCount;
    private List<CommentInfoDto> commentInfoDtoList;

    public PostInfoDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.authorName = post.getAuthor().getName();
        this.authorEmail = post.getAuthor().getEmail();
        this.createDate = CommonUtil.convertLocalDateTimeToFormatString(post.getCreatedDate());
        this.viewCount = post.getViewCount();
        this.likeCount = post.getLikingMembers().size();
        this.didLike = post.getDidLike(SecurityUtil.getCurrentMemberId());
        this.isExistImage = post.getIsExistImages();
        this.imagesCount = post.getPostImagesCount();
        this.commentInfoDtoList = post.getComments().stream().map(CommentInfoDto::new).collect(Collectors.toList());
    }
}
