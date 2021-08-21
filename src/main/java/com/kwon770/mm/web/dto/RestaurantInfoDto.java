package com.kwon770.mm.web.dto;

import com.kwon770.mm.domain.restaurant.*;
import com.kwon770.mm.domain.review.Review;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class RestaurantInfoDto {

    private Long id;
    private String name;
    private String description;
    private Type type;
    private Price price;
    private Location location;
    private boolean deliveryable;
    private Double latitude;
    private Double longitude;
    private Float averageGrade;
    private String openTime;
    private String closeTime;
    private List<RestaurantTheme> themes;
    private List<RestaurantSpecial> specials;
    private List<ReviewInfoDto> reviews;
    private Integer reviewCount;
    private Integer likeCount;

    public RestaurantInfoDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.description = restaurant.getDescription();
        this.type = restaurant.getType();
        this.price = restaurant.getPrice();
        this.location = restaurant.getLocation();
        this.deliveryable = restaurant.getDeliveryable();
        this.latitude = restaurant.getLatitude();
        this.longitude = restaurant.getLongitude();
        this.averageGrade = restaurant.getAverageGrade();
        this.openTime = restaurant.getOpenTime();
        this.closeTime = restaurant.getCloseTime();
        this.themes = restaurant.getThemes();
        this.specials = restaurant.getSpecials();
        this.reviews = convertReviewsToDtos(restaurant.getReviews());
        this.reviewCount = restaurant.getReviewCount();
        this.likeCount = restaurant.getLikeCount();
    }

    private List<ReviewInfoDto> convertReviewsToDtos(List<Review> reviews) {
        return reviews.stream().map(ReviewInfoDto::new).collect(Collectors.toList());
    }
}
