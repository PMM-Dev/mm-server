package com.kwon770.mm.domain.member;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kwon770.mm.domain.report.Report;
import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.web.dto.MemberRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String encodedEmail;

    @Column(nullable = false)
    private String picture;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String socialToken;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialTokenType socialTokenType;

    @Column(nullable = false)
    private Integer reviewCount = 0;

    @ManyToMany
    @JoinTable(
            name = "member_title_relation",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "member_title_id")
    )
    private List<MemberTitle> titles = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "member_restaurant_like_relation",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private List<Restaurant> likedRestaurants = new ArrayList<>();

    @Builder
    public Member(String name, String email, String encodedEmail, String picture, Role role, String socialToken, SocialTokenType socialTokenType) {
        this.name = name;
        this.email = email;
        this.encodedEmail = encodedEmail;
        this.picture = picture;
        this.role = role;
        this.socialToken = socialToken;
        this.socialTokenType = socialTokenType;
    }

    public void update(MemberRequestDto memberRequestDto) {
        this.name = memberRequestDto.getName();
        this.email = memberRequestDto.getEmail();
        this.picture = memberRequestDto.getPicture();
        this.role = memberRequestDto.getRole();
        this.socialToken = memberRequestDto.getSocialToken();
        this.socialTokenType = memberRequestDto.getSocialTokenType();
    }

    public void increaseReviewCount() {
        reviewCount++;
    }

    public void decreaseReviewCount() {
        reviewCount--;
    }

    public void appendTitle(MemberTitle memberTitle) { this.titles.add(memberTitle); }

    public void subtractTitle(MemberTitle memberTitle) { this.titles.remove(memberTitle); }

    public void appendLikedRestaurant(Restaurant restaurant) { this.likedRestaurants.add(restaurant); }

    public void subtractedLikedRestaurant(Restaurant restaurant) { this.likedRestaurants.remove(restaurant); }
}
