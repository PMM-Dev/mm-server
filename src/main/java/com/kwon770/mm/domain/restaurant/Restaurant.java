package com.kwon770.mm.domain.restaurant;


import com.kwon770.mm.RestaurantLocation;
import com.kwon770.mm.RestaurantPrice;
import com.kwon770.mm.RestaurantType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private RestaurantType type;

    @Column(nullable = false)
    private RestaurantPrice price;

    @Column(nullable = false)
    private RestaurantLocation location;

    @Column(nullable = false)
    private Boolean deliveryable;

    @Builder
    public Restaurant(String name, String description,
                      RestaurantType type, RestaurantPrice price, RestaurantLocation location,
                      Boolean deliveryable) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.location = location;
        this.deliveryable = deliveryable;
    }
}
