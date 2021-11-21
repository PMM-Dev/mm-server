package com.kwon770.mm.web.restaurant;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.service.restaurant.RestaurantService;
import com.kwon770.mm.service.member.MemberService;
import com.kwon770.mm.dto.Restaurant.*;
import com.kwon770.mm.service.restaurant.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.kwon770.mm.Utility.isDigit;

@RequiredArgsConstructor
@RestController
public class RestaurantApiController {

    private final RestaurantService restaurantService;
    private final ReviewService reviewService;
    private final MemberService memberService;

    @PostMapping("/restaurant")
    public ResponseEntity<Long> uploadRestaurant(@RequestBody RestaurantRequestDto restaurantRequestDto) {
        Long restaurantId = restaurantService.save(restaurantRequestDto);

        return new ResponseEntity<>(restaurantId, HttpStatus.OK);
    }

    @GetMapping("/restaurant/type/{type}")
    public ResponseEntity<List<RestaurantElementDto>> getRestaurantElementDtosByType(@PathVariable String type) {
        List<RestaurantElementDto> restaurantElementDtos = restaurantService.getRestaurantElementDtosByType(type);

        return new ResponseEntity<>(restaurantElementDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/type/{type}/orderBy/priceDesc")
    public ResponseEntity<List<RestaurantElementDto>> getRestaurantElementDtosByTypeOrderByPriceDesc(@PathVariable String type) {
        List<RestaurantElementDto> restaurantElementDtos = restaurantService.getRestaurantElementDtosByTypeOrderByPriceDesc(type);

        return new ResponseEntity<>(restaurantElementDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/type/{type}/orderBy/priceAsc")
    public ResponseEntity<List<RestaurantElementDto>> getRestaurantElementDtosByTypeOrderByPriceAsc(@PathVariable String type) {
        List<RestaurantElementDto> restaurantElementDtos = restaurantService.getRestaurantElementDtosByTypeOrderByPriceAsc(type);

        return new ResponseEntity<>(restaurantElementDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/type/{type}/orderBy/averageGradeDesc")
    public ResponseEntity<List<RestaurantElementDto>> getRestaurantElementDtosByTypeOrderByAverageGradeDesc(@PathVariable String type) {
        List<RestaurantElementDto> restaurantElementDtos = restaurantService.getRestaurantElementDtosByTypeOrderByAverageGradeDesc(type);

        return new ResponseEntity<>(restaurantElementDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/type/{type}/orderBy/reviewCountDesc")
    public ResponseEntity<List<RestaurantElementDto>> getRestaurantElementDtosByTypeOrderByReviewCountDesc(@PathVariable String type) {
        List<RestaurantElementDto> restaurantElementDtos = restaurantService.getRestaurantElementDtosByTypeOrderByReviewCountDesc(type);

        return new ResponseEntity<>(restaurantElementDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/type/{type}/orderBy/likeCountDesc")
    public ResponseEntity<List<RestaurantElementDto>> getRestaurantElementDtosByTypeOrderByLikeCountDesc(@PathVariable String type) {
        List<RestaurantElementDto> restaurantElementDtos = restaurantService.getRestaurantElementDtosByTypeOrderByLikeCountDesc(type);

        return new ResponseEntity<>(restaurantElementDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/deliverable")
    public ResponseEntity<List<RestaurantElementDto>> getRestaurantElementDtosByDeliverable() {
        List<RestaurantElementDto> restaurantElementDtos = restaurantService.getRestaurantElementDtosByDeliverable();

        return new ResponseEntity<>(restaurantElementDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/rank")
    public ResponseEntity<List<RestaurantElementDto>> getRestaurantElementDtosByRank() {
        // TOP 20 YET
        List<RestaurantElementDto> restaurantElementDtos = restaurantService.getRestaurantElementDtosByRank();

        return new ResponseEntity<>(restaurantElementDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/theme/{theme}")
    public ResponseEntity<List<RestaurantThemeDto>> getRestaurantThemeDtoListByTheme(@PathVariable String theme) {
        List<RestaurantThemeDto> restaurantThemeDtos = restaurantService.getRestaurantThemeDtosByTheme(theme);

        return new ResponseEntity<>(restaurantThemeDtos, HttpStatus.OK);
    }

    @GetMapping("/restaurant/condition")
    public ResponseEntity<RestaurantGachaDto> getRestaurantGachaDtoByMultipleConditions(
            @RequestParam(value = "type", required = false) List<String> type,
            @RequestParam(value = "price", required = false) List<String> price,
            @RequestParam(value = "location", required = false) List<String> location,
            @RequestParam(value = "deliverable", required = false) Boolean deliverable
    ) {
        Optional<RestaurantGachaDto> restaurantGachaDto = restaurantService.getRestaurantGachaDtoByMultipleCondition(type, price, location, deliverable);
        if (restaurantGachaDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(restaurantGachaDto.get(), HttpStatus.OK);
    }

    @GetMapping("/restaurant/{identifier}")
    public ResponseEntity<RestaurantInfoDto> getRestaurantInfoDtoByIdentifier(@PathVariable String identifier) {
        RestaurantInfoDto restaurantInfoDto;
        if (isDigit(identifier)) {
            restaurantInfoDto = restaurantService.getRestaurantInfoDtoById(Long.parseLong(identifier));
        } else {
            restaurantInfoDto = restaurantService.getRestaurantInfoDtoByName(identifier);
        }

        return new ResponseEntity<>(restaurantInfoDto, HttpStatus.OK);
    }

    @GetMapping("/restaurant/location/list")
    public ResponseEntity<List<RestaurantLocationDto>> getAllRestaurantLocationDtoList() {
        List<RestaurantLocationDto> restaurantLocationDtos = restaurantService.getAllRestaurantLocationDtos();

        return new ResponseEntity<>(restaurantLocationDtos, HttpStatus.OK);
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

    @PostMapping("/restaurant/{restaurantId}/review/me")
    public ResponseEntity<Long> uploadMyReviewByRestaurantId(@PathVariable Long restaurantId, @RequestBody ReviewRequestDto reviewRequestDto) {
        Member author = memberService.getMeById();
        Long reviewId = reviewService.uploadMyReviewByRestaurantId(author, restaurantId, reviewRequestDto);

        return new ResponseEntity<>(reviewId, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/review/me")
    public ResponseEntity<ReviewInfoDto> getMyReviewInfoDtoByRestaurantId(@PathVariable Long restaurantId) {
        Optional<ReviewInfoDto> reviewInfoDto = reviewService.getMyReviewInfoDtoByRestaurantId(restaurantId);
        if (reviewInfoDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(reviewInfoDto.get(), HttpStatus.OK);
    }

    @PutMapping("/restaurant/{restaurantId}/review/me")
    public ResponseEntity<Void> updateMyReviewByReviewId(@PathVariable Long restaurantId, @RequestBody ReviewRequestDto reviewRequestDto) {
            reviewService.updateMyReviewByRestaurantId(restaurantId, reviewRequestDto);

            return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/restaurant/{restaurantId}/review/me")
    public ResponseEntity<Void> deleteMyReviewByReviewId(@PathVariable Long restaurantId) {
            reviewService.deleteMyReviewByRestaurantId(restaurantId);

            return new ResponseEntity<>(HttpStatus.OK);
    }
}
