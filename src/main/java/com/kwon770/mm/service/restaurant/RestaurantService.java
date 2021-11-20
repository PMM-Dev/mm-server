package com.kwon770.mm.service.restaurant;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.RestaurantQueryRepository;
import com.kwon770.mm.domain.restaurant.RestaurantRepository;
import com.kwon770.mm.domain.restaurant.Type;
import com.kwon770.mm.domain.restaurant.review.Review;
import com.kwon770.mm.domain.restaurant.review.ReviewRepository;
import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.service.member.MemberService;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.dto.Restaurant.*;
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

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findOneById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_MEMBER_BY_EMAIL + id));
    }

    public RestaurantInfoDto getRestaurantInfoDtoById(Long id) {
        Restaurant restaurant = getRestaurantById(id);

        return RestaurantMapper.INSTANCE.restaurantToRestaurantInfoDto(restaurant);
    }

    public Restaurant getRestaurantByName(String name) {
        return restaurantRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_RESTAURANT_BY_RESTAURANTNAME + name));
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

    public Optional<RestaurantGachaDto> getRestaurantGachaDtoByMultipleCondition(List<String> type, List<String> price, List<String> location, Boolean deliverable) {
        Optional<Restaurant> restaurant = restaurantQueryRepository.findByMultipleConditions(type, price, location, deliverable);
        if (restaurant.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(RestaurantMapper.INSTANCE.restaurantToRestaurantGachaDto(restaurant.get()));
    }

    public List<RestaurantLocationDto> getAllRestaurantLocationDtos() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        return RestaurantMapper.INSTANCE.restaurantToRestaurantLocationDtos(restaurants);
    }

    public Long updateRestaurant(Long restaurantId, RestaurantRequestDto restaurantRequestDto) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        restaurant.update(restaurantRequestDto);

        return restaurant.getId();
    }

    public void deleteRestaurantById(Long id) {
        restaurantRepository.deleteById(id);
    }

    public void deleteRestaurantByName(String name) {
        Restaurant targetRestaurant = getRestaurantByName(name);

        restaurantRepository.delete(targetRestaurant);
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

    @Transactional
    public Optional<ReviewInfoDto> getMyReviewInfoDtoByRestaurantId(Long restaurantId) {
        Optional<Review> review = reviewRepository.findByRestaurant_IdAndAuthor_Id(restaurantId, SecurityUtil.getCurrentMemberId());
        if (review.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(RestaurantMapper.INSTANCE.reviewToReviewInfoDto(review.get()));
    }

    @Transactional
    public void updateMyReviewByRestaurantId(Long restaurantId, ReviewRequestDto reviewRequestDto) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        Optional<Review> review = reviewRepository.findByRestaurant_IdAndAuthor_Id(restaurantId, SecurityUtil.getCurrentMemberId());
        if (review.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_REVIEW_BY_RESTAURANTID + restaurantId);
        }
        Review myReview = review.get();

        restaurant.calculateUpdatedAverageGrade(myReview.getGrade(), reviewRequestDto.getGrade());
        myReview.update(reviewRequestDto);
    }

    @Transactional
    public void deleteMyReviewByRestaurantId(Long restaurantId) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        Optional<Review> review = reviewRepository.findByRestaurant_IdAndAuthor_Id(restaurantId, SecurityUtil.getCurrentMemberId());
        if (review.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_REVIEW_BY_RESTAURANTID + restaurantId);
        }
        Review myReview = review.get();

        restaurant.calculateSubtractedAverageGrade(myReview.getGrade());
        reviewRepository.delete(myReview);
        memberService.getMemberById(SecurityUtil.getCurrentMemberId()).decreaseReviewCount();
    }

    public List<MyReviewDto> getMyReviewList(Long userId) {
        List<Review> reviews = reviewRepository.findAllByAuthor_Id(userId);

        return RestaurantMapper.INSTANCE.reviewsToMyReviewDtos(reviews);
    }
}
