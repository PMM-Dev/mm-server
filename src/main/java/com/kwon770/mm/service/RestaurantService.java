package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.RestaurantQueryRepository;
import com.kwon770.mm.domain.restaurant.RestaurantRepository;
import com.kwon770.mm.web.dto.RestaurantSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;

    public Long save(RestaurantSaveDto restaurantSaveDto) {
        return restaurantRepository.save(restaurantSaveDto.toEntity()).getId();
    }

    public Restaurant findOneById(Long id) {
        return restaurantRepository.findOneById(id);
    }

    public List<Restaurant> findAllByConditions(String type, String price, String location, String deliveryable) {
        return restaurantQueryRepository.findAllByConditions(type, price, location, deliveryable);
    }

    public void delete(Long id) {
        restaurantRepository.delete(findOneById(id));
    }
}
