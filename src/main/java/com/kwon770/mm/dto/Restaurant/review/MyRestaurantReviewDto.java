package com.kwon770.mm.dto.restaurant.review;

import com.kwon770.mm.domain.restaurant.review.RestaurantReview;
import com.kwon770.mm.util.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class MyRestaurantReviewDto {

    private Long id;
    private String createdDate;
    private String authorName;
    private String authorEmail;
    private String authorPicture;
    private String description;
    private Float grade;
    private Long restaurantId;
    private String restaurantName;

    public MyRestaurantReviewDto(RestaurantReview restaurantReview) {
        this.id = restaurantReview.getId();
        this.createdDate = CommonUtil.convertLocalDateTimeToFormatString(restaurantReview.getCreatedDate());
        this.authorName = restaurantReview.getAuthor().getName();
        this.authorEmail = restaurantReview.getAuthor().getEmailFromDbEmail();
        this.authorPicture = restaurantReview.getAuthor().getPicture();
        this.description = restaurantReview.getDescription();
        this.grade = restaurantReview.getGrade();
        this.restaurantId = restaurantReview.getRestaurant().getId();
        this.restaurantName = restaurantReview.getRestaurant().getName();
    }
}
