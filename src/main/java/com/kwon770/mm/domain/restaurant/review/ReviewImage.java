package com.kwon770.mm.domain.restaurant.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Entity
public class ReviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String originalFileName;

    @NotNull
    private String filePath;

    @NotNull
    private Long fileSize;

    @OneToOne(mappedBy = "reviewImage")
    private Review review;

    @Builder
    public ReviewImage(String originalFileName, String filePath, Long fileSize, Review review) {
        this.originalFileName = originalFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.review = review;
    }
}
