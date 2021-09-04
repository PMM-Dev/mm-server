package com.kwon770.mm.web;

import com.kwon770.mm.service.RestaurantPropertyService;
import com.kwon770.mm.web.dto.RestaurantSpecialSaveDto;
import com.kwon770.mm.web.dto.RestaurantThemeSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RestaurantPropertyApiController {

    private final RestaurantPropertyService restaurantPropertyService;

    @PostMapping("/theme")
    public Long saveTheme(@RequestBody RestaurantThemeSaveDto restaurantThemeSaveDto) {
        return restaurantPropertyService.saveTheme(restaurantThemeSaveDto);
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
    public Long saveSpecial(@RequestBody RestaurantSpecialSaveDto restaurantSpecialSaveDto) {
        return restaurantPropertyService.saveSpecial(restaurantSpecialSaveDto);
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
