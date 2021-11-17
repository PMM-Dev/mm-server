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
public class RestaurantImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String originalFileName;

    @NotNull
    private String filePath;

    @NotNull
    private Long fileSize;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Builder
    public RestaurantImage(String originalFileName, String filePath, Long fileSize) {
        this.originalFileName = originalFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
