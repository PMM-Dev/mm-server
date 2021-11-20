package com.kwon770.mm.dto.Restaurant;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {

    private String description;
    private Float grade;

    @Builder
    public ReviewRequestDto(String description, Float grade) {
        this.description = description;
        this.grade = grade;
    }
}
