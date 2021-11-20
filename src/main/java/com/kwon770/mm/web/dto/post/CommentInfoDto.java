package com.kwon770.mm.web.dto.post;

import com.kwon770.mm.domain.post.comment.Comment;
import com.kwon770.mm.util.CommonUtil;
import com.kwon770.mm.util.SecurityUtil;
import lombok.Getter;

@Getter
public class CommentInfoDto {

    private String content;
    private String authorPicture;
    private String authorName;
    private String createDate;
    private int likeCount;
    private boolean didLike;

    public CommentInfoDto(Comment comment) {
        this.content = comment.getContent();
        this.authorPicture = comment.getAuthor().getPicture();
        this.authorName = comment.getAuthor().getName();
        this.createDate = CommonUtil.convertLocalDateTimeToFormatString(comment.getCreatedDate());
        this.likeCount = comment.getLikingMembers().size();
        this.didLike = comment.getDidLike(SecurityUtil.getCurrentMemberId());
    }
}
