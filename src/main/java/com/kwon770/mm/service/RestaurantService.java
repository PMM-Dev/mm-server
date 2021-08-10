package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.RestaurantQueryRepository;
import com.kwon770.mm.domain.restaurant.RestaurantRepository;
import com.kwon770.mm.domain.review.Review;
import com.kwon770.mm.domain.review.ReviewRepository;
import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.web.dto.RestaurantInfoDto;
import com.kwon770.mm.web.dto.RestaurantSaveDto;
import com.kwon770.mm.web.dto.ReviewInfoDto;
import com.kwon770.mm.web.dto.ReviewSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;
    private final ReviewRepository reviewRepository;

    public Long save(RestaurantSaveDto restaurantSaveDto) {
        return restaurantRepository.save(restaurantSaveDto.toEntity()).getId();
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findOneById(id)
                .orElseThrow(() -> new IllegalArgumentException("id가 일치하는 식당이 없습니다. id=" + id));
    }

    public RestaurantInfoDto getRestaurantInfoDtoById(Long id) {
        Restaurant targetRestaurant = getRestaurantById(id);

        return targetRestaurant.toDto();
    }

    public Restaurant getRestaurantByName(String name) {
        return restaurantRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("name이 일치하는 식당이 없습니다. name=" + name));
    }

    public RestaurantInfoDto getRestaurantInfoDtoByName(String name) {
        Restaurant targetRestaurant = getRestaurantByName(name);

        return targetRestaurant.toDto();
    }

    public List<RestaurantInfoDto> getRestaurantsByMultipleCondition(String type, String price, String location, String deliveryable) {
        return mapRestaurantsToDtos(restaurantQueryRepository.findAllByMultipleConditions(type, price, location, deliveryable));

    }

    public void deleteRestaurantById(Long id) {
        Restaurant targetRestaurant = getRestaurantById(id);

        restaurantRepository.delete(targetRestaurant);
    }

    public void deleteRestaurantByName(String name) {
        Restaurant targetRestaurant = getRestaurantByName(name);

        restaurantRepository.delete(targetRestaurant);
    }

    private List<RestaurantInfoDto> mapRestaurantsToDtos(List<Restaurant> restaurants) {
        return restaurants.stream().map(RestaurantInfoDto::new).collect(Collectors.toList());
    }

    public Long uploadReview(User author, Long restaurantId, ReviewSaveDto reviewSaveDto) {
        Restaurant targetRestaurant = getRestaurantById(restaurantId);
        Review reviewEntity = Review.builder()
                .author(author)
                .restaurant(targetRestaurant)
                .description(reviewSaveDto.getDescription())
                .grade(reviewSaveDto.getGrade())
                .build();

        targetRestaurant.calculateAddedAverageGrade(reviewEntity.getGrade());
        targetRestaurant.addReviewCount();

        return reviewRepository.save(reviewEntity).getId();
    }

    public List<ReviewInfoDto> getReviewList(Long restaurantId) {
        Restaurant targetRestaurant = getRestaurantById(restaurantId);

        return mapReviewsToDtos(targetRestaurant.getReviews());
    }

    public void deleteReviewById(Long reviewId) {
        Review review = reviewRepository.getOne(reviewId);
        Restaurant restaurant = review.getRestaurant();

        restaurant.calculateSubtractedAverageGrade(review.getGrade());
        restaurant.subtractReviewCount();

        reviewRepository.deleteById(reviewId);
    }

    private List<ReviewInfoDto> mapReviewsToDtos(List<Review> reviews) {
        return reviews.stream().map((ReviewInfoDto::new)).collect(Collectors.toList());
    }

    public void updateRestaurant(Long restaurantId, RestaurantSaveDto restaurantSaveDto) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        restaurant.update(restaurantSaveDto);
    }

}
