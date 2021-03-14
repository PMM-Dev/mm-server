package com.kwon770.mm.web;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.service.RestaurantService;
import com.kwon770.mm.web.dto.RestaurantSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kwon770.mm.Utility.isDigit;

@RequiredArgsConstructor
@RestController
public class RestauarntApiController {

    private final RestaurantService restaurantService;

    @PostMapping("/api/v1/restaurant/save")
    public Long save(@RequestBody RestaurantSaveDto restaurantSaveDto) {
        return restaurantService.save(restaurantSaveDto);
    }

    @GetMapping("/api/v1/restaurant/list")
    public List<Restaurant> readList() {
        return restaurantService.readList();
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

}
