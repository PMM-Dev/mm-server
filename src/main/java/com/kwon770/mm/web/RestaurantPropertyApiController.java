package com.kwon770.mm.web;

import com.kwon770.mm.service.RestaurantPropertyService;
import com.kwon770.mm.web.dto.Restaurant.RestaurantSpecialRequestDto;
import com.kwon770.mm.web.dto.Restaurant.RestaurantThemeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RestaurantPropertyApiController {

    private final RestaurantPropertyService restaurantPropertyService;

    @PostMapping("/theme")
    public Long saveTheme(@RequestBody RestaurantThemeRequestDto restaurantThemeRequestDto) {
        return restaurantPropertyService.saveTheme(restaurantThemeRequestDto);
    }

    @DeleteMapping("/theme/{theme}")
    public boolean deleteTheme(@PathVariable String theme) {
        restaurantPropertyService.deleteTheme(theme);

        return true;
    }

    @PutMapping("/restaurant/{restaurantId}/theme/{theme}")
    public void appendTheme(@PathVariable Long restaurantId, @PathVariable String theme) {
        restaurantPropertyService.appendTheme(restaurantId, theme);
    }

    @DeleteMapping("/restaurant/{restaurantId}/theme/{theme}")
    public boolean subtractTheme(@PathVariable Long restaurantId, @PathVariable String theme) {
        restaurantPropertyService.subtractTheme(restaurantId, theme);
        return true;
    }

    @PostMapping("/special")
    public Long saveSpecial(@RequestBody RestaurantSpecialRequestDto restaurantSpecialSaveDto) {
        return restaurantPropertyService.saveSpecial(restaurantSpecialSaveDto);
    }

    @DeleteMapping("/special/{special}")
    public boolean deleteSpecial(@PathVariable String special) {
        restaurantPropertyService.deleteSpecial(special);
        return true;
    }

    @PutMapping("/restaurant/{restaurantId}/special/{special}")
    public void appendSpecial(@PathVariable Long restaurantId, @PathVariable String special) {
        restaurantPropertyService.appendSpecial(restaurantId, special);
    }

    @DeleteMapping("/restaurant/{restaurantId}/special/{special}")
    public boolean subtractSpecial(@PathVariable Long restaurantId, @PathVariable String special) {
        restaurantPropertyService.subtractSpecial(restaurantId, special);
        return true;
    }
}
