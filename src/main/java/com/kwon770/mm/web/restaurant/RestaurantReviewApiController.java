package com.kwon770.mm.web.restaurant;

import com.kwon770.mm.dto.restaurant.review.RestaurantReviewInfoDto;
import com.kwon770.mm.dto.restaurant.review.RestaurantReviewRequestDto;
import com.kwon770.mm.service.restaurant.review.RestaurantReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class RestaurantReviewApiController {

    private final RestaurantReviewService restaurantReviewService;

    @PostMapping("/restaurant/{restaurantId}/review")
    public ResponseEntity<Long> uploadMyReviewByRestaurantId(@PathVariable Long restaurantId,
                                                             @RequestParam(value = "description") String description,
                                                             @RequestParam(value = "grade") Float grade,
                                                             @RequestParam(value = "image", required = false) MultipartFile image) {
        RestaurantReviewRequestDto restaurantReviewRequestDto = new RestaurantReviewRequestDto(description, grade, image);
        Long reviewId = restaurantReviewService.uploadMyReviewByRestaurantId(restaurantId, restaurantReviewRequestDto);

        return new ResponseEntity<>(reviewId, HttpStatus.OK);
    }

    @PutMapping("/restaurant/{restaurantId}/review")
    public ResponseEntity<Void> updateMyReviewByRestaurantId(@PathVariable Long restaurantId,
                                                             @RequestParam(value = "description") String description,
                                                             @RequestParam(value = "grade") Float grade,
                                                             @RequestParam(value = "image", required = false) MultipartFile image) {
        RestaurantReviewRequestDto restaurantReviewRequestDto = new RestaurantReviewRequestDto(description, grade, image);
        restaurantReviewService.updateMyReviewByRestaurantId(restaurantId, restaurantReviewRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/review")
    public ResponseEntity<List<RestaurantReviewInfoDto>> getReviewInfoDtosByRestaurantId(@PathVariable Long restaurantId) {
        List<RestaurantReviewInfoDto> restaurantReviewInfoDtos = restaurantReviewService.getReviewInfoDtosByRestaurantId(restaurantId);

        return new ResponseEntity<>(restaurantReviewInfoDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/review/orderBy/dateDesc")
    public ResponseEntity<List<RestaurantReviewInfoDto>> getReviewInfoDtosByRestaurantIdOrderByCreatedDateDesc(@PathVariable Long restaurantId) {
        List<RestaurantReviewInfoDto> restaurantReviewInfoDtos = restaurantReviewService.getReviewInfoDtosByRestaurantIdOrderByCreatedDateDesc(restaurantId);

        return new ResponseEntity<>(restaurantReviewInfoDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/review/orderBy/gradeDesc")
    public ResponseEntity<List<RestaurantReviewInfoDto>> getReviewInfoDtosByRestaurantIdOrderByGradeDesc(@PathVariable Long restaurantId) {
        List<RestaurantReviewInfoDto> restaurantReviewInfoDtos = restaurantReviewService.getReviewInfoDtosByRestaurantIdOrderByGradeDesc(restaurantId);

        return new ResponseEntity<>(restaurantReviewInfoDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/review/orderBy/gradeAsc")
    public ResponseEntity<List<RestaurantReviewInfoDto>> getReviewInfoDtosByRestaurantIdOrderByGradeAsc(@PathVariable Long restaurantId) {
        List<RestaurantReviewInfoDto> restaurantReviewInfoDtos = restaurantReviewService.getReviewInfoDtosByRestaurantIdOrderByGradeAsc(restaurantId);

        return new ResponseEntity<>(restaurantReviewInfoDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/review/me")
    public ResponseEntity<RestaurantReviewInfoDto> getMyReviewInfoDtoByRestaurantId(@PathVariable Long restaurantId) {
        Optional<RestaurantReviewInfoDto> reviewInfoDto = restaurantReviewService.getMyReviewInfoDtoByRestaurantId(restaurantId);
        if (reviewInfoDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(reviewInfoDto.get(), HttpStatus.OK);
    }

    @DeleteMapping("/restaurant/{restaurantId}/review/me")
    public ResponseEntity<Void> deleteMyReviewByReviewId(@PathVariable Long restaurantId) {
        restaurantReviewService.deleteMyReviewByRestaurantId(restaurantId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
