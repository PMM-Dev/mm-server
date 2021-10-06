package com.kwon770.mm.web;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.service.RestaurantService;
import com.kwon770.mm.web.dto.Restaurant.RestaurantRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kwon770.mm.Utility.isDigit;

@RequiredArgsConstructor
@RestController
public class RestaurantAdminApiController {

    private final RestaurantService restaurantService;

    @GetMapping("/restaurant/list")
    public List<Restaurant> getAllRestaurantList() {
        return restaurantService.getAllRestaurants();
    }

    @PutMapping("/restaurant/{restaurantId}")
    public void updateRestaurantById(@PathVariable Long restaurantId, @RequestBody RestaurantRequestDto restaurantRequestDto) {
        restaurantService.updateRestaurant(restaurantId, restaurantRequestDto);
    }

    @DeleteMapping("/restaurant/{identifier}")
    public boolean deleteRestaurantByIdentifier(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            restaurantService.deleteRestaurantById(Long.parseLong(identifier));
        } else {
            restaurantService.deleteRestaurantByName(identifier);
        }
        return true;
    }
}
