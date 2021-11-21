package com.kwon770.mm.service.restaurant;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.review.Review;
import com.kwon770.mm.domain.restaurant.review.ReviewRepository;
import com.kwon770.mm.dto.Restaurant.MyReviewDto;
import com.kwon770.mm.dto.Restaurant.ReviewInfoDto;
import com.kwon770.mm.dto.Restaurant.ReviewRequestDto;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.service.member.MemberService;
import com.kwon770.mm.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantService restaurantService;
    private final MemberService memberService;

    @Transactional
    public Long uploadMyReviewByRestaurantId(Member author, Long restaurantId, ReviewRequestDto reviewRequestDto) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
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
    public Optional<ReviewInfoDto> getMyReviewInfoDtoByRestaurantId(Long restaurantId) {
        Optional<Review> review = reviewRepository.findByRestaurant_IdAndAuthor_Id(restaurantId, SecurityUtil.getCurrentMemberId());
        if (review.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(RestaurantMapper.INSTANCE.reviewToReviewInfoDto(review.get()));
    }

    @Transactional
    public void updateMyReviewByRestaurantId(Long restaurantId, ReviewRequestDto reviewRequestDto) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
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
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
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
