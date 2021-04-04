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

    @PostMapping("/api/v1/restaurant/save")
    public Long save(@RequestBody RestaurantSaveDto restaurantSaveDto) {
        return restaurantService.save(restaurantSaveDto);
    }

    @GetMapping("/api/v1/restaurant/list")
    public List<Restaurant> readList() {
        return restaurantService.readList();
    }

    @GetMapping("/api/v1/restaurant/read/condition")
    public List<Restaurant> readByConditions (
            @RequestParam(value="type", defaultValue = "") String type,
            @RequestParam(value="price", defaultValue = "") String price,
            @RequestParam(value="location", defaultValue = "") String location,
            @RequestParam(value="deliveryable",defaultValue = "") String deliveryable
    ) {
        return restaurantService.findAllByConditions(type, price, location, deliveryable);
    }

    @GetMapping("/api/v1/restaurant/read/{identifier}")
    public Restaurant read(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            return restaurantService.findOneById(Long.parseLong(identifier));
        } else {
            return restaurantService.findByName(identifier);
        }
    }

    @DeleteMapping("/api/v1/restaurant/delete/{identifier}")
    public void delete(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            restaurantService.deleteById(Long.parseLong(identifier));
        } else {
            restaurantService.deleteByName(identifier);
        }
    }

    @PostMapping("/api/v1/restaurant/{restaurantId}/review/save")
    public Long saveReview(@PathVariable Long restaurantId, @RequestBody ReviewSaveDto reviewSaveDto) {
        return restaurantService.saveReview(userService.findByEmail(reviewSaveDto.getAuthorEmail()), restaurantId, reviewSaveDto);
    }

    @GetMapping("/api/v1/restaurant/{restaurantId}/review/list")
    public List<Review> readReviewList(@PathVariable Long restaurantId) { return restaurantService.readReviewList(restaurantId); }

    @DeleteMapping("/api/v1/restaurant/review/delete/{reviewId}")
    public void deleteReviewById(@PathVariable Long reviewId) {
        restaurantService.deleteReviewById(reviewId);
    }
}
