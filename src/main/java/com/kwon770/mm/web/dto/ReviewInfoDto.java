package com.kwon770.mm.web.dto;

import com.kwon770.mm.domain.review.Review;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class ReviewInfoDto {

    private Long id;
    private String authorEmail;
    private String description;
    private Integer grade;

    public ReviewInfoDto(Review review) {
        this.id = review.getId();
        this.authorEmail = review.getAuthor().getEmail();
        this.description = review.getDescription();
        this.grade = review.getGrade();
    }
}
