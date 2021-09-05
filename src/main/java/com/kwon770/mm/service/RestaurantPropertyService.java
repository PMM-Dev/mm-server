package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.*;
import com.kwon770.mm.web.dto.Restaurant.RestaurantSpecialRequestDto;
import com.kwon770.mm.web.dto.Restaurant.RestaurantThemeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RestaurantPropertyService {

    private final RestaurantService restaurantService;
    private final RestaurantThemeRepository restaurantThemeRepository;
    private final RestaurantSpecialRepository restaurantSpecialRepository;

    public Long saveTheme(RestaurantThemeRequestDto restaurantThemeRequestDto) {
        return restaurantThemeRepository.save(restaurantThemeRequestDto.toEntity()).getId();
    }

    public void deleteTheme(String theme) {
        restaurantThemeRepository.delete(restaurantThemeRepository.findByTheme(Theme.valueOf(theme)));
    }

    @Transactional
    public void appendTheme(Long id, String theme) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        RestaurantTheme restaurantTheme = restaurantThemeRepository.findByTheme(Theme.valueOf(theme));

        restaurant.appendTheme(restaurantTheme);
    }

    @Transactional
    public void subtractTheme(Long id, String theme) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        RestaurantTheme restaurantTheme = restaurantThemeRepository.findByTheme(Theme.valueOf(theme));

        restaurant.subtractTheme(restaurantTheme);
    }

    public Long saveSpecial(RestaurantSpecialRequestDto restaurantSpecialRequestDto) {
        return restaurantSpecialRepository.save(restaurantSpecialRequestDto.toEntity()).getId();
    }

    public void deleteSpeical(String special) {
        restaurantSpecialRepository.delete(restaurantSpecialRepository.findBySpecial(Special.valueOf(special)));
    }

    @Transactional
    public void appendSpecial(Long id, String special) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        RestaurantSpecial restaurantSpecial = restaurantSpecialRepository.findBySpecial(Special.valueOf(special));

        restaurant.appendSpecial(restaurantSpecial);
    }

    @Transactional
    public void subtractSpecial(Long id, String special) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        RestaurantSpecial restaurantSpecial = restaurantSpecialRepository.findBySpecial(Special.valueOf(special));

        restaurant.subtractSpecial(restaurantSpecial);
    }
}
