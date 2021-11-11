package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.review.Review;
import com.kwon770.mm.web.dto.Restaurant.*;
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

    default RestaurantElementDto restaurantToRestaurantElementDto(Restaurant restaurant) {
        return new RestaurantElementDto(restaurant);
    }

    List<RestaurantElementDto> restaurantsToRestaurantElementDtos(List<Restaurant> restaurants);

    RestaurantGachaDto restaurantToRestaurantGachaDto(Restaurant restaurant);

    RestaurantLocationDto restaurantToRestaurantLocationDto(Restaurant restaurant);

    List<RestaurantLocationDto> restaurantToRestaurantLocationDtos(List<Restaurant> restaurants);

    default ReviewInfoDto reviewToReviewInfoDto(Review review) {
        return new ReviewInfoDto(review);
    }

    RestaurantSearchDto restaurantToRestaurantSearchDto(Restaurant restaurant);

    List<RestaurantSearchDto> restaurantsToRestaurantSearchDtos(List<Restaurant> restaurants);

    List<ReviewInfoDto> reviewsToReviewInfoDtos(List<Review> reviews);

    default MyReviewDto reviewToMyReviewDto(Review review) {
        return new MyReviewDto(review);
    }

    List<MyReviewDto> reviewsToMyReviewDtos(List<Review> reviews);
}
