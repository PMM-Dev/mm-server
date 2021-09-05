package com.kwon770.mm.web.dto.Restaurant;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {

    private String description;
    private Integer grade;

    @Builder
    public ReviewRequestDto(String description, Integer grade) {
        this.description = description;
        this.grade = grade;
    }
}
