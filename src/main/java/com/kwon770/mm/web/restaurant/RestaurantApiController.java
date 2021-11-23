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

    @PutMapping("/restaurant/{restaurantId}/like")
    public ResponseEntity<Void> likeRestaurant(@PathVariable Long restaurantId) {
        restaurantService.likeRestaurant(restaurantId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/restaurant/{restaurantId}/like")
    public ResponseEntity<Void> unlikeRestaurant(@PathVariable Long restaurantId) {
        restaurantService.unlikeRestaurant(restaurantId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
