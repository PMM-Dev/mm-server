package com.kwon770.mm.service.restaurant;

import com.kwon770.mm.domain.restaurant.*;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.dto.restaurant.RestaurantSpecialRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RestaurantPropertyService {

    private final RestaurantService restaurantService;
    private final RestaurantThemeRepository restaurantThemeRepository;
    private final RestaurantSpecialRepository restaurantSpecialRepository;


    @Transactional
    public void appendTheme(Long restaurantId, String theme) {
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantId(restaurantId);
        Optional<RestaurantTheme> restaurantTheme = restaurantThemeRepository.findByTheme(Theme.valueOf(theme));
        if (restaurantTheme.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_THEME_MESSAGE + theme);
        }

        restaurant.appendTheme(restaurantTheme.get());
    }

    @Transactional
    public void subtractTheme(Long restaurantId, String theme) {
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantId(restaurantId);
        Optional<RestaurantTheme> restaurantTheme = restaurantThemeRepository.findByTheme(Theme.valueOf(theme));
        if (restaurantTheme.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_THEME_MESSAGE + theme);
        }

        restaurant.subtractTheme(restaurantTheme.get());
    }

    public Long saveSpecial(RestaurantSpecialRequestDto restaurantSpecialSaveDto) {
        return restaurantSpecialRepository.save(restaurantSpecialSaveDto.toEntity()).getId();
    }

    public void deleteSpecial(String special) {
        Optional<RestaurantSpecial> restaurantSpecial = restaurantSpecialRepository.findBySpecial(Special.valueOf(special));
        if (restaurantSpecial.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_SPECIAL_MESSAGE + special);
        }

        restaurantSpecialRepository.delete(restaurantSpecial.get());
    }

    @Transactional
    public void appendSpecial(Long restaurantId, String special) {
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantId(restaurantId);
        Optional<RestaurantSpecial> restaurantSpecial = restaurantSpecialRepository.findBySpecial(Special.valueOf(special));
        if (restaurantSpecial.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_SPECIAL_MESSAGE + special);
        }

        restaurant.appendSpecial(restaurantSpecial.get());
    }

    @Transactional
    public void subtractSpecial(Long restaurantId, String special) {
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantId(restaurantId);
        Optional<RestaurantSpecial> restaurantSpecial = restaurantSpecialRepository.findBySpecial(Special.valueOf(special));
        if (restaurantSpecial.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_SPECIAL_MESSAGE + special);
        }

        restaurant.subtractSpecial(restaurantSpecial.get());
    }
}
