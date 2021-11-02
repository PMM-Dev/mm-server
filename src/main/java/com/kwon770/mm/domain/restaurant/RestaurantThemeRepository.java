package com.kwon770.mm.domain.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantThemeRepository extends JpaRepository<RestaurantTheme, Long> {

    Optional<RestaurantTheme> findByTheme(Theme theme);
}
