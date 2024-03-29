package com.kwon770.mm.dto.restaurant;

import com.kwon770.mm.domain.restaurant.Location;
import com.kwon770.mm.domain.restaurant.Price;
import com.kwon770.mm.domain.restaurant.Type;
import com.kwon770.mm.domain.restaurant.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantRequestDto {

    private String name;
    private String description;
    private Type type;
    private Price price;
    private Location location;
    private Boolean deliverable;
    private Double latitude;
    private Double longitude;
    private String openTime;
    private String closeTime;

    public Restaurant toEntity() {
        return Restaurant.builder()
                .name(name)
                .description(description)
                .type(type)
                .price(price)
                .location(location)
                .deliverable(deliverable)
                .latitude(latitude)
                .longitude(longitude)
                .openTime(openTime)
                .closeTime(closeTime)
                .build();
    }
}
