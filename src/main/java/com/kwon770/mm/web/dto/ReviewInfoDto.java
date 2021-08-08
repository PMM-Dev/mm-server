package com.kwon770.mm.web.dto;

import com.kwon770.mm.domain.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewInfoDto {
    
    private String authorEmail;
    private String description;
    private Integer grade;

    public ReviewInfoDto(Review review) {
        this.authorEmail = review.getAuthor().getEmail();
        this.description = review.getDescription();
        this.grade = review.getGrade();
    }
}
