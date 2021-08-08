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
        Restaurant restaurant = restaurantRepository.findOneById(id)
                .orElseThrow(() -> new IllegalArgumentException("id가 일치하는 식당이 없습니다. id=" + id));

        return new RestaurantInfoDto(restaurant);
    }

    public Restaurant getRestaurantByName(String name) {
        return restaurantRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("name이 일치하는 식당이 없습니다. name=" + name));
    }

    public RestaurantInfoDto getRestaurantInfoDtoByName(String name) {
        Restaurant restaurant = restaurantRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("name이 일치하는 식당이 없습니다. name=" + name));

        return new RestaurantInfoDto(restaurant);
    }

    public List<RestaurantInfoDto> getRestaurantsByMultipleCondition(String type, String price, String location, String deliveryable) {
        return restaurantQueryRepository.findAllByMultipleConditions(type, price, location, deliveryable)
                .stream().map(RestaurantInfoDto::new).collect(Collectors.toList());
    }

    public void deleteRestaurantById(Long id) {
        restaurantRepository.delete(getRestaurantById(id));
    }

    public void deleteRestaurantByName(String name) {
        restaurantRepository.delete(getRestaurantByName(name));
    }

    public Long uploadReview(User author, Long restaurantId, ReviewSaveDto reviewSaveDto) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        Review reviewEntity = Review.builder()
                .author(author)
                .restaurant(restaurant)
                .description(reviewSaveDto.getDescription())
                .grade(reviewSaveDto.getGrade())
                .build();

        setAddedGrade(restaurant, reviewSaveDto.getGrade());

        return reviewRepository.save(reviewEntity).getId();
    }

    public List<ReviewInfoDto> getReviewList(Long restaurantId) {
        return getRestaurantById(restaurantId).getReviews().stream().map((ReviewInfoDto::new)).collect(Collectors.toList());
    }

    public void deleteReviewById(Long reviewId) {

        Review review = reviewRepository.getOne(reviewId);
        setSubtractedGrade(review.getRestaurant(), review.getGrade());

        reviewRepository.deleteById(reviewId);
    }

    public void updateRestaurant(Long id, RestaurantSaveDto restaurantSaveDto) {
        Restaurant restaurant = getRestaurantById(id);
        restaurant.update(restaurantSaveDto);
    }

    private void setAddedGrade(Restaurant restaurant, Integer grade) {
        restaurant.addReviewCount();
        restaurant.calculateAverageGrade(grade);
    }

    private void setSubtractedGrade(Restaurant restaurant, Integer grade) {
        restaurant.subtractReviewCount();
        restaurant.calculateAverageGrade(grade);
    }
}
