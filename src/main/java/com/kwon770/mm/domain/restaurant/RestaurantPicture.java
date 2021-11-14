package com.kwon770.mm.domain.restaurant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Entity
public class RestaurantPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String originalFileName;

    @NotNull
    private String filePath;

    private Long fileSize;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Restaurant restaurant;

    @Builder
    public RestaurantPicture(String originalFileName, String filePath, Long fileSize) {
        this.originalFileName = originalFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
