package com.kwon770.mm.web.dto.post;

import com.kwon770.mm.domain.post.Post;
import com.kwon770.mm.util.CommonUtil;

public class PostPreviewDto {

    private Long id;
    private String title;
    private String authorName;
    private String createDate;
    private int viewCount;
    private int likeCount;
    private boolean isExistImage;

    public PostPreviewDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.authorName = post.getAuthor().getName();
        this.createDate = CommonUtil.convertLocalDateTimeToFormatString(post.getCreatedDate());
        this.viewCount = post.getViewCount();
        this.likeCount = post.getLikingMembers().size();
        this.isExistImage = post.getIsExistImages();
    }
}
