package com.kwon770.mm.domain.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findOneById(Long id);

    Restaurant findByName(String name);
}
