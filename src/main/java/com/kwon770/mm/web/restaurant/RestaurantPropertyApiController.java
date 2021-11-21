package com.kwon770.mm.web.restaurant;

import com.kwon770.mm.domain.restaurant.Theme;
import com.kwon770.mm.dto.Restaurant.RestaurantThemeDto;
import com.kwon770.mm.service.restaurant.RestaurantPropertyService;
import com.kwon770.mm.dto.Restaurant.RestaurantSpecialRequestDto;
import com.kwon770.mm.dto.Restaurant.RestaurantThemeRequestDto;
import com.kwon770.mm.service.restaurant.RestaurantService;
import com.kwon770.mm.service.restaurant.RestaurantThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RestaurantPropertyApiController {

    private final RestaurantPropertyService restaurantPropertyService;
    private final RestaurantThemeService restaurantThemeService;


    @PostMapping("/theme")
    public ResponseEntity<Long> saveTheme(@RequestBody RestaurantThemeRequestDto restaurantThemeRequestDto) {
        Long themeId = restaurantThemeService.saveTheme(restaurantThemeRequestDto);

        return new ResponseEntity<>(themeId, HttpStatus.OK);
    }

    @DeleteMapping("/theme/{theme}")
    public ResponseEntity<Void> deleteTheme(@PathVariable String theme) {
        restaurantThemeService.deleteTheme(theme);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/restaurant/{restaurantId}/theme/{theme}")
    public ResponseEntity<Void> appendTheme(@PathVariable Long restaurantId, @PathVariable String theme) {
        restaurantPropertyService.appendTheme(restaurantId, theme);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/restaurant/{restaurantId}/theme/{theme}")
    public ResponseEntity<Void> subtractTheme(@PathVariable Long restaurantId, @PathVariable String theme) {
        restaurantPropertyService.subtractTheme(restaurantId, theme);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/theme/random")
    public ResponseEntity<List<Theme>> getRandom2Themes() {
        List<Theme> themes = restaurantThemeService.getRandom2Themes();

        return new ResponseEntity<>(themes, HttpStatus.OK);
    }

    @PostMapping("/special")
    public ResponseEntity<Long> saveSpecial(@RequestBody RestaurantSpecialRequestDto restaurantSpecialSaveDto) {
        Long specialId = restaurantPropertyService.saveSpecial(restaurantSpecialSaveDto);

        return new ResponseEntity<>(specialId, HttpStatus.OK);
    }

    @DeleteMapping("/special/{special}")
    public ResponseEntity<Void> deleteSpecial(@PathVariable String special) {
        restaurantPropertyService.deleteSpecial(special);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/restaurant/{restaurantId}/special/{special}")
    public ResponseEntity<Void> appendSpecial(@PathVariable Long restaurantId, @PathVariable String special) {
        restaurantPropertyService.appendSpecial(restaurantId, special);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/restaurant/{restaurantId}/special/{special}")
    public ResponseEntity<Void> subtractSpecial(@PathVariable Long restaurantId, @PathVariable String special) {
        restaurantPropertyService.subtractSpecial(restaurantId, special);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
