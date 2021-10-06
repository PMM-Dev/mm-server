package com.kwon770.mm.domain.review;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kwon770.mm.domain.BaseTimeEntity;
import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
}
