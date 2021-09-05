package com.kwon770.mm.web.dto.Restaurant;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.restaurant.*;;
import com.kwon770.mm.util.SecurityUtil;
import lombok.Getter;

import java.util.List;

@Getter
public class RestaurantElementDto {

    private Long id;
    private String name;
    private Type type;
    private Price price;
    private Location location;
    private Float averageGrade;
    private List<RestaurantTheme> themes;
    private List<RestaurantSpecial> specials;

    private boolean didLike;


    public RestaurantElementDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.type = restaurant.getType();
        this.price = restaurant.getPrice();
        this.location = restaurant.getLocation();
        this.averageGrade = restaurant.getAverageGrade();
        this.themes = restaurant.getThemes();
        this.specials = restaurant.getSpecials();

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
