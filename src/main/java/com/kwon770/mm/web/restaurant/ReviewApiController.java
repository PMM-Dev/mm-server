package com.kwon770.mm.web.restaurant;

import com.kwon770.mm.dto.restaurant.ReviewInfoDto;
import com.kwon770.mm.dto.restaurant.ReviewRequestDto;
import com.kwon770.mm.service.restaurant.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping("/restaurant/{restaurantId}/review")
    public ResponseEntity<Long> uploadMyReviewByRestaurantId(@PathVariable Long restaurantId,
                                                             @RequestParam(value = "description") String description,
                                                             @RequestParam(value = "grade") Float grade,
                                                             @RequestParam(value = "image", required = false) MultipartFile image) {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto(description, grade, image);
        Long reviewId = reviewService.uploadMyReviewByRestaurantId(restaurantId, reviewRequestDto);

        return new ResponseEntity<>(reviewId, HttpStatus.OK);
    }

    @PutMapping("/restaurant/{restaurantId}/review")
    public ResponseEntity<Void> updateMyReviewByRestaurantId(@PathVariable Long restaurantId,
                                                             @RequestParam(value = "description") String description,
                                                             @RequestParam(value = "grade") Float grade,
                                                             @RequestParam(value = "image", required = false) MultipartFile image) {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto(description, grade, image);
        reviewService.updateMyReviewByRestaurantId(restaurantId, reviewRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/review")
    public ResponseEntity<List<ReviewInfoDto>> getReviewInfoDtosByRestaurantId(@PathVariable Long restaurantId) {
        List<ReviewInfoDto> reviewInfoDtos = reviewService.getReviewInfoDtosByRestaurantId(restaurantId);

        return new ResponseEntity<>(reviewInfoDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/review/orderBy/dateDesc")
    public ResponseEntity<List<ReviewInfoDto>> getReviewInfoDtosByRestaurantIdOrderByCreatedDateDesc(@PathVariable Long restaurantId) {
        List<ReviewInfoDto> reviewInfoDtos = reviewService.getReviewInfoDtosByRestaurantIdOrderByCreatedDateDesc(restaurantId);

        return new ResponseEntity<>(reviewInfoDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/review/orderBy/gradeDesc")
    public ResponseEntity<List<ReviewInfoDto>> getReviewInfoDtosByRestaurantIdOrderByGradeDesc(@PathVariable Long restaurantId) {
        List<ReviewInfoDto> reviewInfoDtos = reviewService.getReviewInfoDtosByRestaurantIdOrderByGradeDesc(restaurantId);

        return new ResponseEntity<>(reviewInfoDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/review/orderBy/gradeAsc")
    public ResponseEntity<List<ReviewInfoDto>> getReviewInfoDtosByRestaurantIdOrderByGradeAsc(@PathVariable Long restaurantId) {
        List<ReviewInfoDto> reviewInfoDtos = reviewService.getReviewInfoDtosByRestaurantIdOrderByGradeAsc(restaurantId);

        return new ResponseEntity<>(reviewInfoDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/review")
    public ResponseEntity<ReviewInfoDto> getMyReviewInfoDtoByRestaurantId(@PathVariable Long restaurantId) {
        Optional<ReviewInfoDto> reviewInfoDto = reviewService.getMyReviewInfoDtoByRestaurantId(restaurantId);
        if (reviewInfoDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(reviewInfoDto.get(), HttpStatus.OK);
    }

    @DeleteMapping("/restaurant/{restaurantId}/review")
    public ResponseEntity<Void> deleteMyReviewByReviewId(@PathVariable Long restaurantId) {
        reviewService.deleteMyReviewByRestaurantId(restaurantId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
