package com.kwon770.mm.dto.restaurant.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class RestaurantReviewRequestDto {

    private String description;
    private Float grade;
    private Optional<MultipartFile> image;

    @Builder
    public RestaurantReviewRequestDto(String description, Float grade, MultipartFile image) {
        this.description = description;
        this.grade = grade;
        if (image == null) {
            this.image = Optional.empty();
        } else {
            this.image = Optional.of(image);
        }
    }
}
