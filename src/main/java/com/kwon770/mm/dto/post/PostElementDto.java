package com.kwon770.mm.dto.post;

import com.kwon770.mm.domain.post.Post;
import com.kwon770.mm.util.CommonUtil;
import lombok.Getter;

@Getter
public class PostElementDto {

    private Long id;
    private String title;
    private String authorName;
    private String createDate;
    private int viewCount;
    private int likeCount;
    private boolean isExistImage;

    public PostElementDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.authorName = post.getAuthor().getName();
        this.createDate = CommonUtil.convertLocalDateTimeToFormatString(post.getCreatedDate());
        this.viewCount = post.getViewCount();
        this.likeCount = post.getLikingMembers().size();
        this.isExistImage = post.getIsExistImages();
    }
}
