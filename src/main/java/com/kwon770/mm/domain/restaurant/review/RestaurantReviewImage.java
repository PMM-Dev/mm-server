package com.kwon770.mm.domain.restaurant.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Entity
public class RestaurantReviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String originalFileName;

    @NotNull
    private String filePath;

    @NotNull
    private Long fileSize;

    @OneToOne
    @JoinColumn(name = "review_id")
    private RestaurantReview restaurantReview;

    @Builder
    public RestaurantReviewImage(String originalFileName, String filePath, Long fileSize, RestaurantReview restaurantReview) {
        this.originalFileName = originalFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.restaurantReview = restaurantReview;
    }
}
