package com.kwon770.mm.domain.review;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User author;

    //    @JoinColumn(name = "restaurant_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Restaurant restaurant;

    private String description;

    @Column(nullable = false)
    private Integer grade;

    @Column(nullable = false)
    private Integer likeCount = 0;

    @Builder
    public Review(User author, Restaurant restaurant, String description, Integer grade) {
        this.author = author;
        this.restaurant = restaurant;
        this.description = description;
        this.grade = grade;
    }
}
