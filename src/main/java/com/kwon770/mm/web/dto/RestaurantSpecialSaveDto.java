package com.kwon770.mm.web.dto;

import com.kwon770.mm.domain.restaurant.RestaurantSpecial;
import com.kwon770.mm.domain.restaurant.Special;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RestaurantSpecialSaveDto {

    private Special special;

    @Builder
    public RestaurantSpecialSaveDto(Special special) { this.special = special; }

    public RestaurantSpecial toEntity() {
        return RestaurantSpecial.builder()
                .special(special)
                .build();
    }
}
