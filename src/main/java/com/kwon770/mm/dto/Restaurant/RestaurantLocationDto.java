package com.kwon770.mm.dto.Restaurant;

import com.kwon770.mm.domain.restaurant.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantLocationDto {

    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;

    public RestaurantLocationDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.latitude = restaurant.getLatitude();
        this.longitude = restaurant.getLongitude();
    }
}