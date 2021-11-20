package com.kwon770.mm.domain.restaurant.review;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kwon770.mm.domain.BaseTimeEntity;
import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.web.dto.Restaurant.ReviewRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Member author;

    //    @JoinColumn(name = "restaurant_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Restaurant restaurant;

    private String description;

    @Column(nullable = false)
    private Float grade;

    @Column(nullable = false)
    private Integer likeCount = 0;

    @Builder
    public Review(Member author, Restaurant restaurant, String description, Float grade) {
        this.author = author;
        this.restaurant = restaurant;
        this.description = description;
        this.grade = grade;
    }

    public void update(ReviewRequestDto reviewRequestDto) {
        this.description = reviewRequestDto.getDescription();
        this.grade = reviewRequestDto.getGrade();
    }
}
