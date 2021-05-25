package com.kwon770.mm.web.dto;

import com.kwon770.mm.domain.restaurant.RestaurantTheme;
import com.kwon770.mm.domain.restaurant.Theme;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RestaurantThemeSaveDto {

    private Theme theme;

    @Builder
    public RestaurantThemeSaveDto(Theme theme) {
        this.theme = theme;
    }

    public RestaurantTheme toEntity() {
        return RestaurantTheme.builder()
                .theme(theme)
                .build();
    }
}
