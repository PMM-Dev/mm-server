package com.kwon770.mm.web.dto;

import com.kwon770.mm.RestaurantLocation;
import com.kwon770.mm.RestaurantPrice;
import com.kwon770.mm.RestaurantType;
import com.kwon770.mm.domain.restaurant.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantSaveDto {

    private String name;
    private String description;
    private RestaurantType type;
    private RestaurantPrice price;
    private RestaurantLocation location;
    private Boolean deliveryable;
    private Float latitude;
    private Float longitude;

    @Builder
    public RestaurantSaveDto(String name, String description, RestaurantType type,
                             RestaurantPrice price, RestaurantLocation location, Boolean deliveryable,
                             Float latitude, Float longitude) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.location = location;
        this.deliveryable = deliveryable;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Restaurant toEntity() {
        return Restaurant.builder()
                .name(name)
                .description(description)
                .type(type)
                .price(price)
                .location(location)
                .deliveryable(deliveryable)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
