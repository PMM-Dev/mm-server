package com.kwon770.mm.domain.restaurant.review;

import com.kwon770.mm.domain.BaseTimeEntity;
import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.dto.restaurant.review.RestaurantReviewRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Entity
public class RestaurantReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    private String description;

    @NotNull
    private Float grade;

    @NotNull
    private Integer likeCount = 0;

    @OneToOne(mappedBy = "restaurantReview", orphanRemoval = true)
    private RestaurantReviewImage restaurantReviewImage;

    @Builder
    public RestaurantReview(Member author, Restaurant restaurant, String description, Float grade) {
        this.author = author;
        this.restaurant = restaurant;
        this.description = description;
        this.grade = grade;
    }

    public void update(RestaurantReviewRequestDto restaurantReviewRequestDto) {
        this.description = restaurantReviewRequestDto.getDescription();
        this.grade = restaurantReviewRequestDto.getGrade();
    }

    public boolean isExistImage() {
        if (restaurantReviewImage == null) {
            return false;
        }

        return true;
    }
}
