package com.kwon770.mm.web.dto;

import com.kwon770.mm.domain.restaurant.Location;
import com.kwon770.mm.domain.restaurant.Price;
import com.kwon770.mm.domain.restaurant.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class LikedRestaurantDto {

    private Long id;
    private String name;
    private Type type;
    private Price price;
    private Location location;

    @Builder
    LikedRestaurantDto(Long id, String name, Type type, Price price, Location location) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.location = location;
    }
}
