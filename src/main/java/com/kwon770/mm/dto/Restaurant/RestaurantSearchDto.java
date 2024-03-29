package com.kwon770.mm.dto.restaurant;

import com.kwon770.mm.domain.restaurant.Location;
import com.kwon770.mm.domain.restaurant.Price;
import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.Type;
import lombok.Getter;

@Getter
public class RestaurantSearchDto {

    private Long id;
    private String name;
    private Type type;
    private Price price;
    private Location location;

    public RestaurantSearchDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.type = restaurant.getType();
        this.price = restaurant.getPrice();
        this.location = restaurant.getLocation();
    }
}
