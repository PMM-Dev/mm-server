package com.kwon770.mm.dto.feedback;

import com.kwon770.mm.domain.feedback.Feedback;
import com.kwon770.mm.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeedbackRequestDto {

    private String content;

    @Builder
    public FeedbackRequestDto(String content) {
        this.content = content;
    }

    public Feedback toEntity(Member author) {
        return Feedback.builder()
                .author(author)
                .content(content)
                .build();
    }
}
