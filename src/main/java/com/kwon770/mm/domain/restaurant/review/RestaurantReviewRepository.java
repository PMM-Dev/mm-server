package com.kwon770.mm.domain.restaurant.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantReviewRepository extends JpaRepository<RestaurantReview, Long> {

    Optional<RestaurantReview> findByRestaurant_IdAndAuthor_Id(Long restaurantId, Long authorId);

    List<RestaurantReview> findAllByRestaurant_Id(Long restaurantId);

    List<RestaurantReview> findAllByRestaurant_IdOrderByCreatedDateDesc(Long restaurantId);

    List<RestaurantReview> findAllByRestaurant_IdOrderByGradeDesc(Long restaurantId);

    List<RestaurantReview> findAllByRestaurant_IdOrderByGradeAsc(Long restaurantId);

    List<RestaurantReview> findAllByAuthor_Id(Long authorId);
}
