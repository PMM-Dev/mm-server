package com.kwon770.mm.dto.feedback;

import com.kwon770.mm.domain.feedback.Feedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class FeedbackPreviewDto {

    private Long id;
    private String authorEmail;
    private String content;
    private Integer likeCount;

    public FeedbackPreviewDto(Feedback feedback) {
        this.id = feedback.getId();
        this.authorEmail = feedback.getAuthor().getEmailFromDbEmail();
        this.content = feedback.getContent();
        this.likeCount = feedback.getLikingMembers().size();
    }
}
