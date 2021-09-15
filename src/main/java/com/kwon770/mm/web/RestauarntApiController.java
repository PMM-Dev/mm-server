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
import java.util.Optional;

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

    @GetMapping("/restaurant/type/{type}")
    public List<RestaurantElementDto> getRestaurantElementDtosByType(@PathVariable String type) {
        return restaurantService.getRestaurantElementDtosByType(type);
    }

    @GetMapping("/restaurant/condition")
    public RestaurantGachaDto getRestaurantGachaDtoByMultipleConditions(
            @RequestParam(value = "type", required = false) List<String> type,
            @RequestParam(value = "price", required = false) List<String> price,
            @RequestParam(value = "location", required = false) List<String> location,
            @RequestParam(value = "deliveryable", required = false) Boolean deliveryable
    ) {
        return restaurantService.getRestaurantGachaDtoByMultipleCondition(type, price, location, deliveryable);
    }

    @GetMapping("/restaurant/{identifier}")
    public RestaurantInfoDto getRestaurantInfoDtoByIdentifier(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            return restaurantService.getRestaurantInfoDtoById(Long.parseLong(identifier));
        } else {
            return restaurantService.getRestaurantInfoDtoByName(identifier);
        }
    }

    @GetMapping("/restaurant/location/list")
    public List<RestaurantLocationDto> getAllRestaurantLocationDtoList() {
        return restaurantService.getAllRestaurantLocationDtos();
    }

    @GetMapping("/restaurant/{restaurantId}/review")
    public List<ReviewInfoDto> getReviewInfoDtosByRestaurantId(@PathVariable Long restaurantId) {
        return restaurantService.getReviewInfoDtosByRestaurantId(restaurantId);
    }

    @PostMapping("/restaurant/{restaurantId}/review")
    public Long uploadMyReviewByRestaurantId(@PathVariable Long restaurantId, @RequestBody ReviewRequestDto reviewRequestDto) {
        Member author = memberService.getMemberById(SecurityUtil.getCurrentMemberId());

        return restaurantService.uploadMyReviewByRestaurantId(author, restaurantId, reviewRequestDto);
    }

    @GetMapping("/restaurant/{restaurantId}/review/me")
    public ReviewInfoDto getMyReviewInfoDtoByRestaurantId(@PathVariable Long restaurantId) {
        return restaurantService.getMyReviewInfoDtoByRestaurantId(restaurantId);
    }

    @DeleteMapping("/restaurant/{restaurantId}/review/me")
    public void deleteMyReviewByRestaurantId(@PathVariable Long restaurantId) {
        restaurantService.deleteMyReviewByRestaurantId(restaurantId);
    }

}
