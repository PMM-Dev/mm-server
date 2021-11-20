package com.kwon770.mm.dto.Restaurant;

import com.kwon770.mm.domain.restaurant.RestaurantSpecial;
import com.kwon770.mm.domain.restaurant.Special;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RestaurantSpecialRequestDto {

    private Special special;

    @Builder
    public RestaurantSpecialRequestDto(Special special) { this.special = special; }

    public RestaurantSpecial toEntity() {
        return RestaurantSpecial.builder()
                .special(special)
                .build();
    }
}
