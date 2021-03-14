package com.kwon770.mm.web;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.service.RestaurantService;
import com.kwon770.mm.web.dto.RestaurantSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RestauarntApiController {

    private final RestaurantService restaurantService;

    @PostMapping("/api/v1/restaurant/save")
    public Long save(@RequestBody RestaurantSaveDto restaurantSaveDto) {
        return restaurantService.save(restaurantSaveDto);
    }

    @GetMapping("/api/v1/restaurant/info")
    public List<Restaurant> readByConditions (
            @RequestParam(value="type", defaultValue = "") String type,
            @RequestParam(value="price", defaultValue = "") String price,
            @RequestParam(value="location", defaultValue = "") String location,
            @RequestParam(value="deliveryable",defaultValue = "") String deliveryable
    ) {
        return restaurantService.findAllByConditions(type, price, location, deliveryable);
    }

    @DeleteMapping("/api/v1/restaurant/delete/{id}")
    public void delete(@PathVariable String id) {
        restaurantService.delete(Long.parseLong(id));
    }

}
