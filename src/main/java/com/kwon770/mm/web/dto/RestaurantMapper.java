package com.kwon770.mm.web.dto;

import com.kwon770.mm.domain.restaurant.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    List<LikedRestaurantDto> map(List<Restaurant> restaurants);

    default LikedRestaurantDto map(Restaurant restaurant) {
        LikedRestaurantDto likedRestaurantDto = LikedRestaurantDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .type(restaurant.getType())
                .price(restaurant.getPrice())
                .location(restaurant.getLocation())
                .build();

        return likedRestaurantDto;
    }
}
