package com.kwon770.mm.web;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.review.Review;
import com.kwon770.mm.service.RestaurantService;
import com.kwon770.mm.service.UserService;
import com.kwon770.mm.web.dto.RestaurantSaveDto;
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

    @PostMapping("/api/v1/restaurant")
    public Long uploadRestaurant(@RequestBody RestaurantSaveDto restaurantSaveDto) {
        return restaurantService.save(restaurantSaveDto);
    }

    @GetMapping("/api/v1/restaurant/list")
    public List<Restaurant> getRestaurantList() {
        return restaurantService.getRestaurantList();
    }

    @GetMapping("/api/v1/restaurant/condition")
    public List<Restaurant> getRestaurantsByMultipleConditionsWithParameter(
            @RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "price", defaultValue = "") String price,
            @RequestParam(value = "location", defaultValue = "") String location,
            @RequestParam(value = "deliveryable", defaultValue = "") String deliveryable
    ) {
        return restaurantService.getRestaurantsByMultipleCondition(type, price, location, deliveryable);
    }

    @GetMapping("/api/v1/restaurant/{identifier}")
    public Restaurant getRestaurantByIdentifier(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            return restaurantService.getRestaurantById(Long.parseLong(identifier));
        } else {
            return restaurantService.getRestaurantByName(identifier);
        }
    }

    @DeleteMapping("/api/v1/restaurant/{identifier}")
    public void deleteRestaurant(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            restaurantService.deleteRestaurantById(Long.parseLong(identifier));
        } else {
            restaurantService.deleteRestaurantByName(identifier);
        }
    }

    @PostMapping("/api/v1/restaurant/{restaurantId}/review")
    public Long uploadReview(@PathVariable Long restaurantId, @RequestBody ReviewSaveDto reviewSaveDto) {
        return restaurantService.uploadReview(userService.getUserByEmail(reviewSaveDto.getAuthorEmail()), restaurantId, reviewSaveDto);
    }

    @GetMapping("/api/v1/restaurant/{restaurantId}/review/list")
    public List<Review> getReviewList(@PathVariable Long restaurantId) {
        return restaurantService.getReviewList(restaurantId);
    }

    @DeleteMapping("/api/v1/restaurant/review/delete/{reviewId}")
    public void deleteReviewById(@PathVariable Long reviewId) {
        restaurantService.deleteReviewById(reviewId);
    }

}
