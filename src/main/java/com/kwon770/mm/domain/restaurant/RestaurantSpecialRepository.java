package com.kwon770.mm.domain.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantSpecialRepository extends JpaRepository<RestaurantSpecial, Long> {

    Optional<RestaurantSpecial> findBySpecial(Special special);
}
