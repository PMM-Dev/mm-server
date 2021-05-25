package com.kwon770.mm.domain.restaurant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class RestaurantSpecial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Special special;

    @ManyToMany(mappedBy = "specials")
    @JsonBackReference
    private List<Restaurant> restaurant = new ArrayList<>();

    @Builder
    public RestaurantSpecial(Special special) { this.special = special; }
}
