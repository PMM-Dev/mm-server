package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.RestaurantQueryRepository;
import com.kwon770.mm.domain.restaurant.RestaurantRepository;
import com.kwon770.mm.domain.review.Review;
import com.kwon770.mm.domain.review.ReviewRepository;
import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.web.dto.RestaurantSaveDto;
import com.kwon770.mm.web.dto.ReviewSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;
    private final ReviewRepository reviewRepository;

    public Long save(RestaurantSaveDto restaurantSaveDto) {
        return restaurantRepository.save(restaurantSaveDto.toEntity()).getId();
    }

    public List<Restaurant> readList() {
        return restaurantRepository.findAll();
    }

    public Restaurant findOneById(Long id) {
        return restaurantRepository.findOneById(id);
    }

    public Restaurant findByName(String name) {
        return restaurantRepository.findByName(name);
    }

    public List<Restaurant> findAllByConditions(String type, String price, String location, String deliveryable) {
        return restaurantQueryRepository.findAllByConditions(type, price, location, deliveryable);
    }

    public void deleteById(Long id) {
        restaurantRepository.delete(findOneById(id));
    }

    public void deleteByName(String name) { restaurantRepository.delete(findByName(name)); }

    public Long saveReview(User author, Long restaurantId, ReviewSaveDto reviewSaveDto) {
        Review reviewEntity = Review.builder()
                                .author(author)
                                .restaurant(findOneById(restaurantId))
                                .description(reviewSaveDto.getDescription())
                                .grade(reviewSaveDto.getGrade())
                                .build();
        return reviewRepository.save(reviewEntity).getId();
    }

    public List<Review> readReviewList(Long restaurantId) {
        return findOneById(restaurantId).getReviews();
    }

    public void deleteReviewById(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
