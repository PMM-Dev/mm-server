package com.kwon770.mm.service.restaurant;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.review.ReviewImage;
import com.kwon770.mm.domain.restaurant.review.ReviewImageRepository;
import com.kwon770.mm.domain.restaurant.review.Review;
import com.kwon770.mm.domain.restaurant.review.ReviewRepository;
import com.kwon770.mm.dto.restaurant.MyReviewDto;
import com.kwon770.mm.dto.restaurant.ReviewInfoDto;
import com.kwon770.mm.dto.restaurant.ReviewRequestDto;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.service.ImageHandler;
import com.kwon770.mm.service.member.MemberService;
import com.kwon770.mm.util.CommonUtil;
import com.kwon770.mm.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final RestaurantService restaurantService;
    private final MemberService memberService;

    private final ImageHandler imageHandler;

    @Transactional
    public Long uploadMyReviewByRestaurantId(Long restaurantId, ReviewRequestDto reviewRequestDto) {
        Member author = memberService.getMeById();
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantId(restaurantId);
        Review review = Review.builder()
                .author(author)
                .restaurant(restaurant)
                .description(reviewRequestDto.getDescription())
                .grade(reviewRequestDto.getGrade())
                .build();

        if (reviewRequestDto.getImage().isPresent()) {
            ReviewImage reviewImage = generateReviewImage(review, reviewRequestDto.getImage().get());
            reviewImageRepository.save(reviewImage);
        }

        restaurant.calculateAddedAverageGrade(review.getGrade());
        memberService.getMemberById(author.getId()).increaseReviewCount();

        return reviewRepository.save(review).getId();
    }

    private ReviewImage generateReviewImage(Review review, MultipartFile image) {
        ReviewImage reviewImage = imageHandler.parseReviewImage(review, image);
        imageHandler.downloadImage(image, reviewImage.getFilePath());
        return reviewImage;
    }

    @Transactional
    public void updateMyReviewByRestaurantId(Long restaurantId, ReviewRequestDto reviewRequestDto) {
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantId(restaurantId);
        Optional<Review> review = reviewRepository.findByRestaurant_IdAndAuthor_Id(restaurantId, SecurityUtil.getCurrentMemberId());
        if (review.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_REVIEW_BY_RESTAURANTID + restaurantId);
        }
        Review myReview = review.get();

        validateAuthor(myReview.getId());


        if (myReview.getReviewImage() != null) {
            CommonUtil.removeImageFromServer(myReview.getReviewImage().getFilePath());
            reviewImageRepository.delete(myReview.getReviewImage());
        }

        if (reviewRequestDto.getImage().isPresent()) {
            ReviewImage reviewImage = generateReviewImage(myReview, reviewRequestDto.getImage().get());
            reviewImageRepository.save(reviewImage);
        }

        restaurant.calculateUpdatedAverageGrade(myReview.getGrade(), reviewRequestDto.getGrade());
        myReview.update(reviewRequestDto);
    }

    public Review getReviewByReviewId(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_REVIEW_BY_REVIEWID + reviewId));
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

    public Optional<ReviewInfoDto> getMyReviewInfoDtoByRestaurantId(Long restaurantId) {
        Optional<Review> review = reviewRepository.findByRestaurant_IdAndAuthor_Id(restaurantId, SecurityUtil.getCurrentMemberId());
        if (review.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(RestaurantMapper.INSTANCE.reviewToReviewInfoDto(review.get()));
    }

    public Optional<String> getReviewImageByReviewId(Long reviewId) {
        ReviewImage reviewImage = getReviewByReviewId(reviewId).getReviewImage();
        if (reviewImage == null) {
            return Optional.empty();
        }

        return Optional.of(reviewImage.getFilePath());
    }

    public List<MyReviewDto> getMyReviewList(Long userId) {
        List<Review> reviews = reviewRepository.findAllByAuthor_Id(userId);

        return RestaurantMapper.INSTANCE.reviewsToMyReviewDtos(reviews);
    }

    @Transactional
    public void deleteMyReviewByRestaurantId(Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantId(restaurantId);
        Optional<Review> review = reviewRepository.findByRestaurant_IdAndAuthor_Id(restaurantId, SecurityUtil.getCurrentMemberId());
        if (review.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_REVIEW_BY_RESTAURANTID + restaurantId);
        }
        Review myReview = review.get();

        validateAuthor(review.get().getId());

        restaurant.calculateSubtractedAverageGrade(myReview.getGrade());
        reviewRepository.delete(myReview);
        memberService.getMemberById(SecurityUtil.getCurrentMemberId()).decreaseReviewCount();
    }

    public void validateAuthor(Long reviewId) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Review review = getReviewByReviewId(reviewId);
        if (!review.getAuthor().getId().equals(currentMemberId)) {
            throw new IllegalArgumentException(ErrorCode.NOT_AUTHOR_MESSAGE + reviewId);
        }
    }
}
