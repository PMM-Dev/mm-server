package com.kwon770.mm.domain.review;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User author;

    @ManyToOne
    private Restaurant restaurant;

    private String description;

    @Column(nullable = false)
    private Integer grade;

    @Column(nullable = false)
    private Integer likeCount = 0;
}
