package com.kwon770.mm.web.dto.post;

import com.kwon770.mm.domain.post.Post;
import com.kwon770.mm.util.CommonUtil;
import com.kwon770.mm.util.SecurityUtil;

public class PostInfoDto {

    private Long id;
    private String title;
    private String content;
    private String authorName;
    private String createDate;
    private int imagesCount;
    private int viewCount;
    private int likeCount;
    private boolean didLike;
    private boolean isExistImage;

    public PostInfoDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.authorName = post.getAuthor().getName();
        this.createDate = CommonUtil.convertLocalDateTimeToFormatString(post.getCreatedDate());
        this.imagesCount = post.getPostImagesCount();
        this.viewCount = post.getViewCount();
        this.likeCount = post.getLikingMembers().size();
        this.didLike = post.getDidLike(SecurityUtil.getCurrentMemberId());
        this.isExistImage = post.getIsExistImages();
    }
}
