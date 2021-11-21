package com.kwon770.mm.service.restaurant;

import com.kwon770.mm.domain.restaurant.RestaurantTheme;
import com.kwon770.mm.domain.restaurant.RestaurantThemeRepository;
import com.kwon770.mm.domain.restaurant.Theme;
import com.kwon770.mm.dto.Restaurant.RestaurantThemeRequestDto;
import com.kwon770.mm.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class RestaurantThemeService {

    private final RestaurantThemeRepository restaurantThemeRepository;

    public Long saveTheme(RestaurantThemeRequestDto restaurantThemeRequestDto) {
        return restaurantThemeRepository.save(restaurantThemeRequestDto.toEntity()).getId();
    }

    public void deleteTheme(String theme) {
        Optional<RestaurantTheme> restaurantTheme = restaurantThemeRepository.findByTheme(Theme.valueOf(theme));
        if (restaurantTheme.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_THEME_MESSAGE + theme);
        }

        restaurantThemeRepository.delete(restaurantTheme.get());
    }

    @Transactional
    public List<Theme> getRandom2Themes() {
        List<Theme> themes = new ArrayList<>();

        Random random = new Random();
        Long themeCount = restaurantThemeRepository.count();
        int firstIndex = random.nextInt(Math.toIntExact(themeCount));
        Page<RestaurantTheme> firstPage = restaurantThemeRepository.findAll(PageRequest.of(firstIndex, 1));
        if (firstPage.hasContent()) {
            themes.add(firstPage.getContent().get(0).getTheme());
        }

        if (themeCount < 2) {
            return themes;
        }

        int secondIndex = firstIndex;
        while (secondIndex == firstIndex) {
            secondIndex = random.nextInt(Math.toIntExact(themeCount));
        }
        Page<RestaurantTheme> secondPage = restaurantThemeRepository.findAll(PageRequest.of(secondIndex, 1));
        if (secondPage.hasContent()) {
            themes.add(secondPage.getContent().get(0).getTheme());
        }

        return themes;
    }

    public RestaurantTheme findRestaurantThemeByTheme(String theme) {
        Optional<RestaurantTheme> restaurantTheme = restaurantThemeRepository.findByTheme(Theme.valueOf(theme));
        if (restaurantTheme.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_THEME_MESSAGE + theme);
        }

        return restaurantTheme.get();
    }
}
