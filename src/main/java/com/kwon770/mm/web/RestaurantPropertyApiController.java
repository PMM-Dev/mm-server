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
    public void deleteTheme(@PathVariable String theme) {
        restaurantPropertyService.deleteTheme(theme);
    }

    @PutMapping("/restaurant/{id}/theme/{theme}")
    public void appendTheme(@PathVariable Long id, @PathVariable String theme) {
        restaurantPropertyService.appendTheme(id, theme);
    }

    @DeleteMapping("/restaurant/{id}/theme/{theme}")
    public void subtractTheme(@PathVariable Long id, @PathVariable String theme) {
        restaurantPropertyService.subtractTheme(id, theme);
    }

    @PostMapping("/special")
    public Long saveSpecial(@RequestBody RestaurantSpecialRequestDto restaurantSpecialRequestDto) {
        return restaurantPropertyService.saveSpecial(restaurantSpecialRequestDto);
    }

    @DeleteMapping("/special/{special}")
    public void deleteSpecial(@PathVariable String special) {
        restaurantPropertyService.deleteSpeical(special);
    }

    @PutMapping("/restaurant/{id}/special/{special}")
    public void appendSpecial(@PathVariable Long id, @PathVariable String special) {
        restaurantPropertyService.appendSpecial(id, special);
    }

    @DeleteMapping("/restaurant/{id}/special/{special}")
    public void subtractSpecial(@PathVariable Long id, @PathVariable String special) {
        restaurantPropertyService.subtractSpecial(id, special);
    }
}
