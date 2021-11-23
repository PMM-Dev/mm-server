package com.kwon770.mm.dto.feedback;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.feedback.Feedback;
import com.kwon770.mm.util.CommonUtil;
import com.kwon770.mm.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class FeedbackInfoDto {

    private Long id;
    private String createdDate;
    private String authorEmail;
    private String authorPicture;
    private String content;
    private Integer likeCount;

    private boolean didLike;

    public FeedbackInfoDto(Feedback feedback) {
        this.id = feedback.getId();
        this.createdDate = CommonUtil.convertLocalDateTimeToFormatString(feedback.getCreatedDate());
        this.authorEmail = feedback.getAuthor().getEmail();
        this.authorPicture = feedback.getAuthor().getPicture();
        this.content = feedback.getContent();
        this.likeCount = feedback.getLikingMembers().size();

        calculateDidLike(feedback.getLikingMembers());
    }

    private void calculateDidLike(List<Member> LikingMembers) {
        Long userId = SecurityUtil.getCurrentMemberId();
        for (Member likedMember : LikingMembers) {
            if (userId.equals(likedMember.getId())) {
                this.didLike = true;
                break;
            }
        }
    }
}
