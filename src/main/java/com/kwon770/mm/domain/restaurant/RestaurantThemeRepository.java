package com.kwon770.mm.domain.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantThemeRepository extends JpaRepository<RestaurantTheme, Long> {

    RestaurantTheme findByTheme(Theme theme);
}
