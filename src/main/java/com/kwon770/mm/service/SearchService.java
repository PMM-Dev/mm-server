package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.RestaurantRepository;
import com.kwon770.mm.web.dto.Restaurant.RestaurantSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final RestaurantRepository restaurantRepository;

    public List<RestaurantSearchDto> searchRestaurantByKeyword(String keyword) {
        List<Restaurant> searchedRestaurants = restaurantRepository.findLimit10ByNameContaining(keyword);

        return RestaurantMapper.INSTANCE.restaurantsToRestaurantSearchDtos(searchedRestaurants);
    }
}
