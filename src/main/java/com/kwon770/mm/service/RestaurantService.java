package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.RestaurantQueryRepository;
import com.kwon770.mm.domain.restaurant.RestaurantRepository;
import com.kwon770.mm.domain.restaurant.Type;
import com.kwon770.mm.domain.review.Review;
import com.kwon770.mm.domain.review.ReviewRepository;
import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.web.dto.Restaurant.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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

    public List<Restaurant> getAllRestaurants() {
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
        List<Restaurant> restaurants = restaurantRepository.findAllByType(Type.valueOf(type));

        return RestaurantMapper.INSTANCE.restaurantsToRestaurantElementDtos(restaurants);
    }

    public List<RestaurantElementDto> getRestaurantElementDtosByTypeOrderByPriceDesc(String type) {
        List<Restaurant> restaurants = restaurantRepository.findAllByTypeOrderByPriceDesc(Type.valueOf(type));

        return RestaurantMapper.INSTANCE.restaurantsToRestaurantElementDtos(restaurants);
    }

    public List<RestaurantElementDto> getRestaurantElementDtosByTypeOrderByPriceAsc(String type) {
        List<Restaurant> restaurants = restaurantRepository.findAllByTypeOrderByPriceAsc(Type.valueOf(type));

        return RestaurantMapper.INSTANCE.restaurantsToRestaurantElementDtos(restaurants);
    }

    public List<RestaurantElementDto> getRestaurantElementDtosByTypeOrderByAverageGradeDesc(String type) {
        List<Restaurant> restaurants = restaurantRepository.findAllByTypeOrderByAverageGradeDesc(Type.valueOf(type));

        return RestaurantMapper.INSTANCE.restaurantsToRestaurantElementDtos(restaurants);
    }

    public List<RestaurantElementDto> getRestaurantElementDtosByTypeOrderByReviewCountDesc(String type) {
        List<Restaurant> restaurants = restaurantQueryRepository.findAllByTypeOrderByReviewCountDesc(type);

        return RestaurantMapper.INSTANCE.restaurantsToRestaurantElementDtos(restaurants);
    }

    public List<RestaurantElementDto> getRestaurantElementDtosByTypeOrderByLikeCountDesc(String type) {
        List<Restaurant> restaurants = restaurantQueryRepository.findAllByTypeOrderByLikeCountDesc(type);

        return RestaurantMapper.INSTANCE.restaurantsToRestaurantElementDtos(restaurants);
    }

    public List<RestaurantElementDto> getRestaurantElementDtosByDeliverable() {
        List<Restaurant> restaurants = restaurantRepository.findAllByDeliverableTrue();

        return RestaurantMapper.INSTANCE.restaurantsToRestaurantElementDtos(restaurants);
    }

    public List<RestaurantElementDto> getRestaurantElementDtosByRank() {
        List<Restaurant> restaurants = restaurantRepository.findLimit20ByOrderByAverageGradeDesc();

        return RestaurantMapper.INSTANCE.restaurantsToRestaurantElementDtos(restaurants);
    }

    public RestaurantGachaDto getRestaurantGachaDtoByMultipleCondition(List<String> type, List<String> price, List<String> location, Boolean deliverable) throws IllegalArgumentException {
        Optional<Restaurant> restaurant = restaurantQueryRepository.findByMultipleConditions(type, price, location, deliverable);
        if (!restaurant.isPresent()) {
            throw new IllegalArgumentException("해당 조건을 충족하는 식당이 없습니다.");
        }

        return RestaurantMapper.INSTANCE.restaurantToRestaurantGachaDto(restaurant.get());
    }

    public List<RestaurantLocationDto> getAllRestaurantLocationDtos() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        return RestaurantMapper.INSTANCE.restaurantToRestaurantLocationDtos(restaurants);
    }

    public void deleteRestaurantById(Long id) {
        restaurantRepository.deleteById(id);
    }

    public void deleteRestaurantByName(String name) {
        Restaurant targetRestaurant = getRestaurantByName(name);

        restaurantRepository.delete(targetRestaurant);
    }

    @Transactional
    public Long uploadMyReviewByRestaurantId(Member author, Long restaurantId, ReviewRequestDto reviewRequestDto) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        Review review = Review.builder()
                .author(author)
                .restaurant(restaurant)
                .description(reviewRequestDto.getDescription())
                .grade(reviewRequestDto.getGrade())
                .build();

        restaurant.calculateAddedAverageGrade(review.getGrade());

        memberService.getMemberById(author.getId()).increaseReviewCount();

        return reviewRepository.save(review).getId();
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
        List<Review> reviews = reviewRepository.findAllByRestaurant_Id(restaurantId);

        return RestaurantMapper.INSTANCE.reviewsToReviewInfoDtos(reviews);
    }

    public List<ReviewInfoDto> getReviewInfoDtosByRestaurantIdOrderByCreatedDateDesc(Long restaurantId) {
        List<Review> reviews = reviewRepository.findAllByRestaurant_IdOrderByCreatedDateDesc(restaurantId);

        return RestaurantMapper.INSTANCE.reviewsToReviewInfoDtos(reviews);
    }

    public List<ReviewInfoDto> getReviewInfoDtosByRestaurantIdOrderByGradeDesc(Long restaurantId) {
        List<Review> reviews = reviewRepository.findAllByRestaurant_IdOrderByGradeDesc(restaurantId);

        return RestaurantMapper.INSTANCE.reviewsToReviewInfoDtos(reviews);
    }

    public List<ReviewInfoDto> getReviewInfoDtosByRestaurantIdOrderByGradeAsc(Long restaurantId) {
        List<Review> reviews = reviewRepository.findAllByRestaurant_IdOrderByGradeAsc(restaurantId);

        return RestaurantMapper.INSTANCE.reviewsToReviewInfoDtos(reviews);
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

        ReviewInfoDto reviewInfoDto = getMyReviewInfoDtoByRestaurantId(restaurantId);
        restaurant.calculateSubtractedAverageGrade(reviewInfoDto.getGrade());

        reviewRepository.deleteById(reviewId);

        memberService.getMemberById(myId).decreaseReviewCount();
    }

    public void updateRestaurant(Long restaurantId, RestaurantRequestDto restaurantRequestDto) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        restaurant.update(restaurantRequestDto);
    }

}
