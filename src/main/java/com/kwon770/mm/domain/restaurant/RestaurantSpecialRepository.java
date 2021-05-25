package com.kwon770.mm.domain.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantSpecialRepository extends JpaRepository<RestaurantSpecial, Long> {

    RestaurantSpecial findBySpecial(Special special);
}
