package com.kwon770.mm.service.restaurant.review;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.review.RestaurantReviewImage;
import com.kwon770.mm.domain.restaurant.review.RestaurantReviewImageRepository;
import com.kwon770.mm.domain.restaurant.review.RestaurantReview;
import com.kwon770.mm.domain.restaurant.review.RestaurantReviewRepository;
import com.kwon770.mm.dto.restaurant.review.MyRestaurantReviewDto;
import com.kwon770.mm.dto.restaurant.review.RestaurantReviewInfoDto;
import com.kwon770.mm.dto.restaurant.review.RestaurantReviewRequestDto;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.service.ImageHandler;
import com.kwon770.mm.service.member.MemberService;
import com.kwon770.mm.service.restaurant.RestaurantService;
import com.kwon770.mm.util.CommonUtil;
import com.kwon770.mm.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RestaurantReviewService {

    private final RestaurantReviewRepository restaurantReviewRepository;
    private final RestaurantReviewImageRepository restaurantReviewImageRepository;
    private final RestaurantService restaurantService;
    private final MemberService memberService;

    private final ImageHandler imageHandler;

    @Transactional
    public Long uploadMyReviewByRestaurantId(Long restaurantId, RestaurantReviewRequestDto restaurantReviewRequestDto) {
        Member author = memberService.getMeById();
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantId(restaurantId);
        RestaurantReview restaurantReview = RestaurantReview.builder()
                .author(author)
                .restaurant(restaurant)
                .description(restaurantReviewRequestDto.getDescription())
                .grade(restaurantReviewRequestDto.getGrade())
                .build();

        if (restaurantReviewRequestDto.getImage().isPresent()) {
            RestaurantReviewImage restaurantReviewImage = generateReviewImage(restaurantReview, restaurantReviewRequestDto.getImage().get());
            restaurantReviewImageRepository.save(restaurantReviewImage);
        }

        restaurant.calculateAddedAverageGrade(restaurantReview.getGrade());

        return restaurantReviewRepository.save(restaurantReview).getId();
    }

    private RestaurantReviewImage generateReviewImage(RestaurantReview restaurantReview, MultipartFile image) {
        RestaurantReviewImage restaurantReviewImage = imageHandler.parseReviewImage(restaurantReview, image);
        imageHandler.downloadImage(image, restaurantReviewImage.getFilePath());
        return restaurantReviewImage;
    }

    @Transactional
    public void updateMyReviewByRestaurantId(Long restaurantId, RestaurantReviewRequestDto restaurantReviewRequestDto) {
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantId(restaurantId);
        Optional<RestaurantReview> review = restaurantReviewRepository.findByRestaurant_IdAndAuthor_Id(restaurantId, SecurityUtil.getCurrentMemberId());
        if (review.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_REVIEW_BY_RESTAURANTID + restaurantId);
        }
        RestaurantReview myRestaurantReview = review.get();

        validateAuthor(myRestaurantReview.getId());


        if (myRestaurantReview.getRestaurantReviewImage() != null) {
            CommonUtil.removeImageFromServer(myRestaurantReview.getRestaurantReviewImage().getFilePath());
            restaurantReviewImageRepository.delete(myRestaurantReview.getRestaurantReviewImage());
        }

        if (restaurantReviewRequestDto.getImage().isPresent()) {
            RestaurantReviewImage restaurantReviewImage = generateReviewImage(myRestaurantReview, restaurantReviewRequestDto.getImage().get());
            restaurantReviewImageRepository.save(restaurantReviewImage);
        }

        restaurant.calculateUpdatedAverageGrade(myRestaurantReview.getGrade(), restaurantReviewRequestDto.getGrade());
        myRestaurantReview.update(restaurantReviewRequestDto);
    }

    public RestaurantReview getReviewByReviewId(Long reviewId) {
        return restaurantReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_REVIEW_BY_REVIEWID + reviewId));
    }

    public List<RestaurantReviewInfoDto> getReviewInfoDtosByRestaurantId(Long restaurantId) {
        List<RestaurantReview> restaurantReviews = restaurantReviewRepository.findAllByRestaurant_Id(restaurantId);

        return restaurantReviews.stream().map(RestaurantReviewInfoDto::new).collect(Collectors.toList());
    }

    public List<RestaurantReviewInfoDto> getReviewInfoDtosByRestaurantIdOrderByCreatedDateDesc(Long restaurantId) {
        List<RestaurantReview> restaurantReviews = restaurantReviewRepository.findAllByRestaurant_IdOrderByCreatedDateDesc(restaurantId);

        return restaurantReviews.stream().map(RestaurantReviewInfoDto::new).collect(Collectors.toList());
    }

    public List<RestaurantReviewInfoDto> getReviewInfoDtosByRestaurantIdOrderByGradeDesc(Long restaurantId) {
        List<RestaurantReview> restaurantReviews = restaurantReviewRepository.findAllByRestaurant_IdOrderByGradeDesc(restaurantId);

        return restaurantReviews.stream().map(RestaurantReviewInfoDto::new).collect(Collectors.toList());
    }

    public List<RestaurantReviewInfoDto> getReviewInfoDtosByRestaurantIdOrderByGradeAsc(Long restaurantId) {
        List<RestaurantReview> restaurantReviews = restaurantReviewRepository.findAllByRestaurant_IdOrderByGradeAsc(restaurantId);

        return restaurantReviews.stream().map(RestaurantReviewInfoDto::new).collect(Collectors.toList());
    }

    public Optional<RestaurantReviewInfoDto> getMyReviewInfoDtoByRestaurantId(Long restaurantId) {
        Optional<RestaurantReview> review = restaurantReviewRepository.findByRestaurant_IdAndAuthor_Id(restaurantId, SecurityUtil.getCurrentMemberId());
        if (review.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new RestaurantReviewInfoDto(review.get()));
    }

    public Optional<String> getReviewImageByReviewId(Long reviewId) {
        RestaurantReviewImage restaurantReviewImage = getReviewByReviewId(reviewId).getRestaurantReviewImage();
        if (restaurantReviewImage == null) {
            return Optional.empty();
        }

        return Optional.of(restaurantReviewImage.getFilePath());
    }

    public String getReviewImageFileNameByReviewId(Long reviewId) {
        RestaurantReviewImage restaurantReviewImage = getReviewByReviewId(reviewId).getRestaurantReviewImage();
        if (restaurantReviewImage == null) {
            throw new IllegalArgumentException(ErrorCode.NO_IMAGE_BY_REVIEWID + reviewId);
        }

        return restaurantReviewImage.getFilePath().split("review/")[1];
    }

    public List<MyRestaurantReviewDto> getMyReviewList(Long userId) {
        List<RestaurantReview> restaurantReviews = restaurantReviewRepository.findAllByAuthor_Id(userId);

        return restaurantReviews.stream().map(MyRestaurantReviewDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void deleteMyReviewByRestaurantId(Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantId(restaurantId);
        Optional<RestaurantReview> review = restaurantReviewRepository.findByRestaurant_IdAndAuthor_Id(restaurantId, SecurityUtil.getCurrentMemberId());
        if (review.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_REVIEW_BY_RESTAURANTID + restaurantId);
        }
        RestaurantReview myRestaurantReview = review.get();

        validateAuthor(review.get().getId());

        restaurant.calculateSubtractedAverageGrade(myRestaurantReview.getGrade());
        restaurantReviewRepository.delete(myRestaurantReview);
    }

    public void validateAuthor(Long reviewId) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        RestaurantReview restaurantReview = getReviewByReviewId(reviewId);
        if (!restaurantReview.getAuthor().getId().equals(currentMemberId)) {
            throw new IllegalArgumentException(ErrorCode.NOT_AUTHOR_MESSAGE + reviewId);
        }
    }
}
