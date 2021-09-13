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

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final MemberService memberService;
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

    public List<RestaurantElementDto> getRestaurantElementDtosByType(String type) {
        List<Restaurant> restaurants = restaurantQueryRepository.findAllByType(type);

        return RestaurantMapper.INSTANCE.restaurantsToRestaurantElementDtos(restaurants);
    }

    public RestaurantGachaDto getRestaurantGachaDtoByMultipleCondition(String type, String price, String location, String deliveryable) throws IllegalArgumentException {
        Optional<Restaurant> restaurant = restaurantQueryRepository.findByMultipleConditions(type, price, location, deliveryable);
        if (!restaurant.isPresent()) {
            throw new IllegalArgumentException("해당 조건을 충족하는 식당이 없습니다.");
        }

        return RestaurantMapper.INSTANCE.restaurantToRestaurantGachaDto(restaurant.get());
    }

    public void deleteRestaurantById(Long id) {
        Restaurant targetRestaurant = getRestaurantById(id);

        restaurantRepository.delete(targetRestaurant);
    }

    public void deleteRestaurantByName(String name) {
        Restaurant targetRestaurant = getRestaurantByName(name);

        restaurantRepository.delete(targetRestaurant);
    }

    @Transactional
    public Long uploadMyReviewByRestaurantId(Member author, Long restaurantId, ReviewRequestDto reviewRequestDto) {
        Restaurant targetRestaurant = getRestaurantById(restaurantId);
        Review reviewEntity = Review.builder()
                .author(author)
                .restaurant(targetRestaurant)
                .description(reviewRequestDto.getDescription())
                .grade(reviewRequestDto.getGrade())
                .build();

        targetRestaurant.calculateAddedAverageGrade(reviewEntity.getGrade());

        memberService.getMemberById(author.getId()).increaseReviewCount();

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

    @Transactional
    public void deleteMyReviewByRestaurantId(Long restaurantId) throws IllegalArgumentException {
        Long reviewId = -1L;
        Restaurant restaurant = getRestaurantById(restaurantId);
        Long myId = SecurityUtil.getCurrentMemberId();
        for (Review review : restaurant.getReviews()) {
            if (myId.equals(review.getAuthor().getId())) {
                reviewId = review.getId();
            }
        }

        if (reviewId == -1L) {
            throw new IllegalArgumentException("해당 식당에 작성한 리뷰가 없습니다. Restaurant Id = " + restaurantId);
        }

        reviewRepository.deleteById(reviewId);

        memberService.getMemberById(myId).decreaseReviewCount();
    }

    public void updateRestaurant(Long restaurantId, RestaurantRequestDto restaurantRequestDto) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        restaurant.update(restaurantRequestDto);
    }

}
