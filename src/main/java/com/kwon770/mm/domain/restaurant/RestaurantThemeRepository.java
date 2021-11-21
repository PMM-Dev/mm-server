package com.kwon770.mm.domain.restaurant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantThemeRepository extends JpaRepository<RestaurantTheme, Long> {

    Optional<RestaurantTheme> findByTheme(Theme theme);

    Page<RestaurantTheme> findAll(Pageable pageable);
}
