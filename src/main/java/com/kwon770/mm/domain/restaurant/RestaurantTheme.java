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
public class RestaurantTheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Theme theme;

    @ManyToMany(mappedBy = "themes")
    @JsonBackReference
    private List<Restaurant> restaurant = new ArrayList<>();

    @Builder
    public RestaurantTheme(Theme theme) {
        this.theme  = theme;
    }

}
