package com.kwon770.mm.web;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.service.RestaurantService;
import com.kwon770.mm.service.MemberService;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.web.dto.Restaurant.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kwon770.mm.Utility.isDigit;

@RequiredArgsConstructor
@RestController
public class RestauarntApiController {

    private final RestaurantService restaurantService;
    private final MemberService memberService;

    @PostMapping("/restaurant")
    public Long uploadRestaurant(@RequestBody RestaurantRequestDto restaurantRequestDto) {
        return restaurantService.save(restaurantRequestDto);
    }

    @GetMapping("/restaurant/list")
    public List<Restaurant> getAllRestaurantList() {
        return restaurantService.getRestaurantList();
    }

    @GetMapping("/restaurant/condition")
    public List<RestaurantElementDto> getRestaurantElementDtosByMultipleConditions(
            @RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "price", defaultValue = "") String price,
            @RequestParam(value = "location", defaultValue = "") String location,
            @RequestParam(value = "deliveryable", defaultValue = "") String deliveryable
    ) {
        return restaurantService.getRestaurantsByMultipleCondition(type, price, location, deliveryable);
    }

    @GetMapping("/restaurant/{identifier}")
    public RestaurantInfoDto getRestaurantInfoDtoByIdentifier(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            return restaurantService.getRestaurantInfoDtoById(Long.parseLong(identifier));
        } else {
            return restaurantService.getRestaurantInfoDtoByName(identifier);
        }
    }

    @PutMapping("/restaurant/{restaurantId}")
    public void updateRestaurantById(@PathVariable Long restaurantId, @RequestBody RestaurantRequestDto restaurantRequestDto) {
        restaurantService.updateRestaurant(restaurantId, restaurantRequestDto);
    }

    @DeleteMapping("/restaurant/{identifier}")
    public void deleteRestaurantByIdentifier(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            restaurantService.deleteRestaurantById(Long.parseLong(identifier));
        } else {
            restaurantService.deleteRestaurantByName(identifier);
        }
    }

    @PostMapping("/restaurant/{restaurantId}/review")
    public Long uploadReview(@PathVariable Long restaurantId, @RequestBody ReviewRequestDto reviewRequestDto) {
        Member author = memberService.getMemberById(SecurityUtil.getCurrentMemberId());

        return restaurantService.uploadReview(author, restaurantId, reviewRequestDto);
    }

    @GetMapping("/restaurant/{restaurantId}/review")
    public List<ReviewInfoDto> getReviewListByRestaurantId(@PathVariable Long restaurantId) {
        return restaurantService.getReviewList(restaurantId);
    }

    @DeleteMapping("/review/{reviewId}")
    public void deleteReviewById(@PathVariable Long reviewId) {
        restaurantService.deleteReviewById(reviewId);
    }

}
