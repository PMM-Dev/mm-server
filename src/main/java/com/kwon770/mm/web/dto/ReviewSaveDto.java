package com.kwon770.mm.web.dto;

import com.kwon770.mm.domain.review.Review;
import com.kwon770.mm.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewSaveDto {

    private String authorEmail;
    private String description;
    private Integer grade;

    @Builder
    public ReviewSaveDto(String authorEmail, String description, Integer grade) {
        this.authorEmail = authorEmail;
        this.description = description;
        this.grade = grade;
    }
}
