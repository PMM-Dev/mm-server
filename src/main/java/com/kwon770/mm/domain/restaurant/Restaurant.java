package com.kwon770.mm.domain.restaurant;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kwon770.mm.domain.review.Review;
import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.web.dto.RestaurantSaveDto;
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

    @Column(nullable = false)
    private Integer reviewCount = 0;

    @ManyToMany(mappedBy = "likedRestaurants")
    @JsonBackReference
    private List<User> likingUsers = new ArrayList<>();

    @Column(nullable = false)
    private Integer likeCount = 0;

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

    public void update(RestaurantSaveDto restaurantSaveDto) {
        this.name = restaurantSaveDto.getName();
        this.description = restaurantSaveDto.getDescription();
        this.type = restaurantSaveDto.getType();
        this.price = restaurantSaveDto.getPrice();
        this.location = restaurantSaveDto.getLocation();
        this.deliveryable = restaurantSaveDto.getDeliveryable();
        this.latitude = restaurantSaveDto.getLatitude();
        this.longitude = restaurantSaveDto.getLongitude();
        this.openTime = restaurantSaveDto.getOpenTime();
        this.closeTime = restaurantSaveDto.getCloseTime();
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

    public void addReviewCount() {
        reviewCount++;
    }

    public void subtractReviewCount() {
        reviewCount--;
    }

    public void calculateAverageGrade(Integer newGrade) {
        Float sum = (averageGrade * (reviewCount - 1)) + newGrade;
        averageGrade = sum / reviewCount;
    }

    public void addLikeCount() { likeCount++;}

    public void subtractLikeCount() { likeCount--; }
}
