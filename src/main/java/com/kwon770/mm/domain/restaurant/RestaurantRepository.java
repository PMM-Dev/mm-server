package com.kwon770.mm.domain.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findOneById(Long id);

    Optional<Restaurant> findByName(String name);

    List<Restaurant> findAllByDeliverableTrue();

    List<Restaurant> findTop20ByOrderByAverageGradeDesc();

    List<Restaurant> findAllByType(Type type);

    List<Restaurant> findAllByTypeOrderByPriceDesc(Type type);

    List<Restaurant> findAllByTypeOrderByPriceAsc(Type type);

    List<Restaurant> findAllByTypeOrderByAverageGradeDesc(Type type);

    List<Restaurant> findAllByThemesContaining(RestaurantTheme restaurantTheme);

    List<Restaurant> findTop10ByNameContaining(String keyword);
}
