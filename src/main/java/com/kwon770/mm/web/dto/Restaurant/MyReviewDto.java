package com.kwon770.mm.web.dto.Restaurant;

import com.kwon770.mm.domain.restaurant.review.Review;
import com.kwon770.mm.util.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class MyReviewDto {

    private Long id;
    private String createdDate;
    private String authorName;
    private String authorEmail;
    private String authorPicture;
    private String description;
    private Float grade;
    private Long restaurantId;
    private String restaurantName;

    public MyReviewDto(Review review) {
        this.id = review.getId();
        this.createdDate = CommonUtil.convertLocalDateTimeToFormatString(review.getCreatedDate());
        this.authorName = review.getAuthor().getName();
        this.authorEmail = review.getAuthor().getEmail();
        this.authorPicture = review.getAuthor().getPicture();
        this.description = review.getDescription();
        this.grade = review.getGrade();
        this.restaurantId = review.getRestaurant().getId();
        this.restaurantName = review.getRestaurant().getName();
    }
}
