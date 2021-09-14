package com.kwon770.mm.domain.restaurant;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kwon770.mm.domain.review.Review;
import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.web.dto.Restaurant.RestaurantRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private Price price;

    @Column(nullable = false)
    private Location location;

    @Column(nullable = false)
    private Boolean deliveryable;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Float averageGrade = 0.0F;

    private String openTime;

    private String closeTime;

    @ManyToMany
    @JoinTable(
            name = "restaurant_theme_relation",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_theme_id")
    )
    private List<RestaurantTheme> themes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "restaurant_special_relation",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_special_id")
    )
    private List<RestaurantSpecial> specials = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "likedRestaurants")
    @JsonBackReference
    private List<Member> likingMembers = new ArrayList<>();

    @Builder
    public Restaurant(String name, String description,
                      Type type, Price price, Location location,
                      Boolean deliveryable, Double latitude, Double longitude,
                      String openTime, String closeTime) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.location = location;
        this.deliveryable = deliveryable;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public Float getAverageGrade() {
        Double result = Math.ceil(averageGrade * 2) / 2;
        return result.floatValue();
    }

    public void update(RestaurantRequestDto restaurantRequestDto) {
        this.name = restaurantRequestDto.getName();
        this.description = restaurantRequestDto.getDescription();
        this.type = restaurantRequestDto.getType();
        this.price = restaurantRequestDto.getPrice();
        this.location = restaurantRequestDto.getLocation();
        this.deliveryable = restaurantRequestDto.getDeliveryable();
        this.latitude = restaurantRequestDto.getLatitude();
        this.longitude = restaurantRequestDto.getLongitude();
        this.openTime = restaurantRequestDto.getOpenTime();
        this.closeTime = restaurantRequestDto.getCloseTime();
    }

    public void appendTheme(RestaurantTheme restaurantTheme) {
        this.themes.add(restaurantTheme);
    }

    public void subtractTheme(RestaurantTheme restaurantTheme) {
        this.themes.remove(restaurantTheme);
    }

    public void appendSpecial(RestaurantSpecial restaurantSpecial) {
        this.specials.add(restaurantSpecial);
    }

    public void subtractSpecial(RestaurantSpecial restaurantSpecial) {
        this.specials.remove(restaurantSpecial);
    }

    public void calculateAddedAverageGrade(Float newGrade) {
        int reviewCount = reviews.size();
        Float sum = (averageGrade * reviewCount) + newGrade;
        averageGrade = sum / (reviewCount + 1);
    }

    public void calculateSubtractedAverageGrade(Float newGrade) {
        int reviewCount = reviews.size();
        Float sum = (averageGrade * reviewCount) - newGrade;
        averageGrade = sum / (reviewCount - 1);
    }
}
