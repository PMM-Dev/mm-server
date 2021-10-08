package com.kwon770.mm.web.dto.Restaurant;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.restaurant.*;
import com.kwon770.mm.util.SecurityUtil;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RestaurantInfoDto {

    private Long id;
    private String name;
    private String description;
    private Type type;
    private Price price;
    private Location location;
    private Double latitude;
    private Double longitude;
    private String openTime;
    private String closeTime;
    private boolean deliverable;
    private Float averageGrade;
    private List<RestaurantTheme> themes;
    private List<RestaurantSpecial> specials;
    private List<ReviewInfoDto> reviews;
    private Integer reviewCount;
    private Integer likeCount;

    private boolean didLike;

    public RestaurantInfoDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.description = restaurant.getDescription();
        this.type = restaurant.getType();
        this.price = restaurant.getPrice();
        this.location = restaurant.getLocation();
        this.deliverable = restaurant.getDeliverable();
        this.latitude = restaurant.getLatitude();
        this.longitude = restaurant.getLongitude();
        this.averageGrade = restaurant.getAverageGrade();
        this.openTime = restaurant.getOpenTime();
        this.closeTime = restaurant.getCloseTime();
        this.themes = restaurant.getThemes();
        this.specials = restaurant.getSpecials();
        this.reviews = restaurant.getReviews().stream().map(ReviewInfoDto::new).collect(Collectors.toList());
        this.reviewCount = restaurant.getReviews().size();
        this.likeCount = restaurant.getLikingMembers().size();

        calculateDidLike(restaurant.getLikingMembers());
    }

    private void calculateDidLike(List<Member> LikingMembers) {
        Long userId = SecurityUtil.getCurrentMemberId();
        for (Member likedMember : LikingMembers) {
            if (userId.equals(likedMember.getId())) {
                this.didLike = true;
                break;
            }
        }
    }
}
