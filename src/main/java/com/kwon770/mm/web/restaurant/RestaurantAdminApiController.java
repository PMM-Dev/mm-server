package com.kwon770.mm.web.restaurant;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.service.restaurant.RestaurantService;
import com.kwon770.mm.web.dto.Restaurant.RestaurantRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kwon770.mm.Utility.isDigit;

@RequiredArgsConstructor
@RestController
public class RestaurantAdminApiController {

    private final RestaurantService restaurantService;

    @GetMapping("/restaurant/list")
    public ResponseEntity<List<Restaurant>> getAllRestaurantList() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @PutMapping("/restaurant/{restaurantId}")
    public ResponseEntity<Void> updateRestaurantById(@PathVariable Long restaurantId, @RequestBody RestaurantRequestDto restaurantRequestDto) {
        restaurantService.updateRestaurant(restaurantId, restaurantRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/restaurant/{identifier}")
    public ResponseEntity<Void> deleteRestaurantByIdentifier(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            restaurantService.deleteRestaurantById(Long.parseLong(identifier));
        } else {
            restaurantService.deleteRestaurantByName(identifier);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
