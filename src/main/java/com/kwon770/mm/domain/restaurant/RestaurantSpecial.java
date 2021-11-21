package com.kwon770.mm.domain.restaurant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class RestaurantSpecial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Special special;

    @ManyToMany(mappedBy = "specials")
    @JsonBackReference
    private List<Restaurant> restaurant = new ArrayList<>();

    @Builder
    public RestaurantSpecial(Special special) { this.special = special; }
}
