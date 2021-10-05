package com.kwon770.mm.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByRestaurant_Id(Long restaurantId);

    List<Review> findAllByRestaurant_IdOrderByCreatedDateDesc(Long restaurantId);

    List<Review> findAllByRestaurant_IdOrderByGradeDesc(Long restaurantId);

    List<Review> findAllByRestaurant_IdOrderByGradeAsc(Long restaurantId);

    List<Review> findAllByAuthor_Id(Long userId);
}
