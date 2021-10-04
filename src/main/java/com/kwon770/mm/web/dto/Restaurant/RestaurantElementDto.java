package com.kwon770.mm.web.dto.Restaurant;

import com.kwon770.mm.domain.restaurant.*;;
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
    private Integer likeCount;
    private List<RestaurantTheme> themes;
    private List<RestaurantSpecial> specials;


    public RestaurantElementDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.type = restaurant.getType();
        this.price = restaurant.getPrice();
        this.location = restaurant.getLocation();
        this.averageGrade = restaurant.getAverageGrade();
        this.likeCount = restaurant.getLikingMembers().size();
        this.themes = restaurant.getThemes();
        this.specials = restaurant.getSpecials();
    }
}
