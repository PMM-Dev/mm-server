package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.review.Review;
import com.kwon770.mm.web.dto.LikedRestaurantDto;
import com.kwon770.mm.web.dto.Restaurant.RestaurantElementDto;
import com.kwon770.mm.web.dto.Restaurant.RestaurantInfoDto;
import com.kwon770.mm.web.dto.Restaurant.ReviewInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    List<RestaurantInfoDto> restaurantsToRestaurantInfoDtos(List<Restaurant> restaurants);

    default RestaurantInfoDto restaurantToRestaurantInfoDto(Restaurant restaurant) {
        return new RestaurantInfoDto(restaurant);
    }

    List<LikedRestaurantDto> restaurantsToLikedRestaurantDtos(List<Restaurant> restaurants);

    default RestaurantElementDto restaurantToRestaurantElementDto(Restaurant restaurant) {
        return new RestaurantElementDto(restaurant);
    }

    List<RestaurantElementDto> restaurantsToRestaurantElementDtos(List<Restaurant> restaurants);

    LikedRestaurantDto restaurantToLikedRestaurantDto(Restaurant restaurant);

    List<ReviewInfoDto> reviewsToReviewInfoDtos(List<Review> reviews);

    @Mapping(target = "authorEmail", expression = "java(review.getAuthor().getEmail())")
    ReviewInfoDto reviewToReviewInfoDto(Review review);
}