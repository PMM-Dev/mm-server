package com.kwon770.mm.web;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.review.Review;
import com.kwon770.mm.service.RestaurantService;
import com.kwon770.mm.service.UserService;
import com.kwon770.mm.web.dto.RestaurantInfoDto;
import com.kwon770.mm.web.dto.RestaurantSaveDto;
import com.kwon770.mm.web.dto.ReviewInfoDto;
import com.kwon770.mm.web.dto.ReviewSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kwon770.mm.Utility.isDigit;

@RequiredArgsConstructor
@RestController
public class RestauarntApiController {

    private final RestaurantService restaurantService;
    private final UserService userService;

    @PostMapping("/api/restaurant")
    public Long uploadRestaurant(@RequestBody RestaurantSaveDto restaurantSaveDto) {
        return restaurantService.save(restaurantSaveDto);
    }

    @GetMapping("/api/restaurant/list")
    public List<Restaurant> getRestaurantList() {
        return restaurantService.getRestaurantList();
    }

    @GetMapping("/api/restaurant/condition")
    public List<RestaurantInfoDto> getRestaurantsByMultipleConditionsWithParameter(
            @RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "price", defaultValue = "") String price,
            @RequestParam(value = "location", defaultValue = "") String location,
            @RequestParam(value = "deliveryable", defaultValue = "") String deliveryable
    ) {
        return restaurantService.getRestaurantsByMultipleCondition(type, price, location, deliveryable);
    }

    @GetMapping("/api/restaurant/{identifier}")
    public RestaurantInfoDto getRestaurantByIdentifier(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            return restaurantService.getRestaurantInfoDtoById(Long.parseLong(identifier));
        } else {
            return restaurantService.getRestaurantInfoDtoByName(identifier);
        }
    }

    @PutMapping("/api/restaurant/{id}")
    public void updateRestaurantById(@PathVariable Long id, @RequestBody RestaurantSaveDto restaurantSaveDto) {
        restaurantService.updateRestaurant(id, restaurantSaveDto);
    }

    @DeleteMapping("/api/restaurant/{identifier}")
    public void deleteRestaurant(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            restaurantService.deleteRestaurantById(Long.parseLong(identifier));
        } else {
            restaurantService.deleteRestaurantByName(identifier);
        }
    }

    @PostMapping("/api/restaurant/{restaurantId}/review")
    public Long uploadReview(@PathVariable Long restaurantId, @RequestBody ReviewSaveDto reviewSaveDto) {
        return restaurantService.uploadReview(userService.getUserByEmail(reviewSaveDto.getAuthorEmail()), restaurantId, reviewSaveDto);
    }

    @GetMapping("/api/restaurant/{restaurantId}/review/list")
    public List<ReviewInfoDto> getReviewListByRestaurantId(@PathVariable Long restaurantId) {
        return restaurantService.getReviewList(restaurantId);
    }

    @DeleteMapping("/api/restaurant/review/delete/{reviewId}")
    public void deleteReviewById(@PathVariable Long reviewId) {
        restaurantService.deleteReviewById(reviewId);
    }

}
