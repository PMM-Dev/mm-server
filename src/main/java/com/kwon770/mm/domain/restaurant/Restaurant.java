package com.kwon770.mm.domain.restaurant;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kwon770.mm.RestaurantLocation;
import com.kwon770.mm.RestaurantPrice;
import com.kwon770.mm.RestaurantType;
import com.kwon770.mm.domain.review.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
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

    @Column(nullable = false)
    private Float latitude;

    @Column(nullable = false)
    private Float longitude;

    @Column(nullable = false)
    private Float averageGrade = 0.0F;

    @OneToMany(mappedBy = "restaurant")
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Restaurant(String name, String description,
                      RestaurantType type, RestaurantPrice price, RestaurantLocation location,
                      Boolean deliveryable, Float latitude, Float longitude) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.location = location;
        this.deliveryable = deliveryable;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
