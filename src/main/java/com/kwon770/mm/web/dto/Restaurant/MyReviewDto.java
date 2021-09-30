package com.kwon770.mm.web.dto.Restaurant;

import com.kwon770.mm.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class MyReviewDto {

    private String description;
    private Float grade;
    private Long restaurantId;
    private String restaurantName;

    public MyReviewDto(Review review) {
        this.description = review.getDescription();
        this.grade = review.getGrade();
        this.restaurantId = review.getRestaurant().getId();
        this.restaurantName = review.getRestaurant().getName();
    }
}
