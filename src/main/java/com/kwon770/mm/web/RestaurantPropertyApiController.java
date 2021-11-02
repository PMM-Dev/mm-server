package com.kwon770.mm.web;

import com.kwon770.mm.service.RestaurantPropertyService;
import com.kwon770.mm.web.dto.Restaurant.RestaurantSpecialRequestDto;
import com.kwon770.mm.web.dto.Restaurant.RestaurantThemeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RestaurantPropertyApiController {

    private final RestaurantPropertyService restaurantPropertyService;

    @PostMapping("/theme")
    public ResponseEntity<Long> saveTheme(@RequestBody RestaurantThemeRequestDto restaurantThemeRequestDto) {
        Long themeId = restaurantPropertyService.saveTheme(restaurantThemeRequestDto);

        return new ResponseEntity<>(themeId, HttpStatus.OK);
    }

    @DeleteMapping("/theme/{theme}")
    public ResponseEntity<Void> deleteTheme(@PathVariable String theme) {
        restaurantPropertyService.deleteTheme(theme);

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
