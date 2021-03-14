package com.kwon770.mm.domain.restaurant;

import com.kwon770.mm.RestaurantType;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findOneById(Long id);

    Restaurant findByName(String name);
}
