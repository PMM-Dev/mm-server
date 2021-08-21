package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.review.Review;
import com.kwon770.mm.web.dto.LikedRestaurantDto;
import com.kwon770.mm.web.dto.RestaurantInfoDto;
import com.kwon770.mm.web.dto.ReviewInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    List<RestaurantInfoDto> restaurantsToRestaurantInfoDtos(List<Restaurant> restaurants);

    RestaurantInfoDto restaurantToRestaurantInfoDto(Restaurant restaurant);

    List<LikedRestaurantDto> restaurantsToLikedRestaurantDtos(List<Restaurant> restaurants);

    LikedRestaurantDto restaurantToLikedRestaurantDto(Restaurant restaurant);

    List<ReviewInfoDto> reviewsToReviewInfoDtos(List<Review> reviews);

    @Mapping(target = "authorEmail", expression = "java(review.getAuthor().getEmail())")
    ReviewInfoDto reviewToReviewInfoDto(Review review);
}
