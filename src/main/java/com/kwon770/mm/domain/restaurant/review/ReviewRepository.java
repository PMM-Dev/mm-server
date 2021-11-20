package com.kwon770.mm.domain.restaurant.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByRestaurant_IdAndAuthor_Id(Long restaurantId, Long authorId);

    List<Review> findAllByRestaurant_Id(Long restaurantId);

    List<Review> findAllByRestaurant_IdOrderByCreatedDateDesc(Long restaurantId);

    List<Review> findAllByRestaurant_IdOrderByGradeDesc(Long restaurantId);

    List<Review> findAllByRestaurant_IdOrderByGradeAsc(Long restaurantId);

    List<Review> findAllByAuthor_Id(Long authorId);
}
