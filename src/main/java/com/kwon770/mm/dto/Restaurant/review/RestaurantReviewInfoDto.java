package com.kwon770.mm.dto.restaurant.review;

import com.kwon770.mm.domain.restaurant.review.RestaurantReview;
import com.kwon770.mm.util.CommonUtil;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class RestaurantReviewInfoDto {

    private Long id;
    private String createdDate;
    private String authorName;
    private String authorEmail;
    private String authorPicture;
    private String description;
    private Float grade;
    private boolean isExistImage;

    public RestaurantReviewInfoDto(RestaurantReview restaurantReview) {
        this.id = restaurantReview.getId();
        this.createdDate = CommonUtil.convertLocalDateTimeToFormatString(restaurantReview.getCreatedDate());
        this.authorName = restaurantReview.getAuthor().getName();
        this.authorEmail = restaurantReview.getAuthor().getEmailFromDbEmail();
        this.authorPicture = restaurantReview.getAuthor().getPicture();
        this.description = restaurantReview.getDescription();
        this.grade = restaurantReview.getGrade();
        this.isExistImage = restaurantReview.isExistImage();
    }
}
