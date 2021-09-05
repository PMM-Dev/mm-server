package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.RestaurantQueryRepository;
import com.kwon770.mm.domain.restaurant.RestaurantRepository;
import com.kwon770.mm.domain.review.Review;
import com.kwon770.mm.domain.review.ReviewRepository;
import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.web.dto.Restaurant.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;
    private final ReviewRepository reviewRepository;

    public Long save(RestaurantRequestDto restaurantRequestDto) {
        return restaurantRepository.save(restaurantRequestDto.toEntity()).getId();
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findOneById(id)
                .orElseThrow(() -> new IllegalArgumentException("id가 일치하는 식당이 없습니다. id=" + id));
    }

    public RestaurantInfoDto getRestaurantInfoDtoById(Long id) {
        Restaurant restaurant = getRestaurantById(id);

        return RestaurantMapper.INSTANCE.restaurantToRestaurantInfoDto(restaurant);
    }

    public Restaurant getRestaurantByName(String name) {
        return restaurantRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("name 이 일치하는 식당이 없습니다. name=" + name));
    }

    public RestaurantInfoDto getRestaurantInfoDtoByName(String name) {
        Restaurant restaurant = getRestaurantByName(name);

        return RestaurantMapper.INSTANCE.restaurantToRestaurantInfoDto(restaurant);
    }

    public List<RestaurantElementDto> getRestaurantsByMultipleCondition(String type, String price, String location, String deliveryable) {
        List<Restaurant> restaurants = restaurantQueryRepository.findAllByMultipleConditions(type, price, location, deliveryable);

        return RestaurantMapper.INSTANCE.restaurantsToRestaurantElementDtos(restaurants);
    }

    public void deleteRestaurantById(Long id) {
        Restaurant targetRestaurant = getRestaurantById(id);

        restaurantRepository.delete(targetRestaurant);
    }

    public void deleteRestaurantByName(String name) {
        Restaurant targetRestaurant = getRestaurantByName(name);

        restaurantRepository.delete(targetRestaurant);
    }

    public Long uploadReview(Member author, Long restaurantId, ReviewRequestDto reviewRequestDto) {
        Restaurant targetRestaurant = getRestaurantById(restaurantId);
        Review reviewEntity = Review.builder()
                .author(author)
                .restaurant(targetRestaurant)
                .description(reviewRequestDto.getDescription())
                .grade(reviewRequestDto.getGrade())
                .build();

        targetRestaurant.calculateAddedAverageGrade(reviewEntity.getGrade());
        targetRestaurant.addReviewCount();

        return reviewRepository.save(reviewEntity).getId();
    }

    public ReviewInfoDto getMyReviewInfoDtoByRestaurantId(Long restaurantId) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        Long myId = SecurityUtil.getCurrentMemberId();
        for (Review review : restaurant.getReviews()) {
            if (myId.equals(review.getAuthor().getId())) {
                return RestaurantMapper.INSTANCE.reviewToReviewInfoDto(review);
            }
        }

        return null;
    }

    public List<ReviewInfoDto> getReviewInfoDtosByRestaurantId(Long restaurantId) {
        Restaurant restaurant = getRestaurantById(restaurantId);

        return RestaurantMapper.INSTANCE.reviewsToReviewInfoDtos(restaurant.getReviews());
    }

    public void deleteMyReviewByRestaurantId(Long restaurantId) throws IllegalArgumentException {
        Long reviewId = -1L;
        Restaurant restaurant = getRestaurantById(restaurantId);
        Long myId = SecurityUtil.getCurrentMemberId();
        for (Review review : restaurant.getReviews()) {
            if (myId.equals(review.getId())) {
                reviewId = review.getId();
            }
        }

        if (reviewId == -1L) {
            throw new IllegalArgumentException("해당 식당에 작성한 리뷰가 없습니다. Restaurant Id = " + restaurantId);
        }

        reviewRepository.deleteById(reviewId);
    }

    public void updateRestaurant(Long restaurantId, RestaurantRequestDto restaurantRequestDto) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        restaurant.update(restaurantRequestDto);
    }

}
