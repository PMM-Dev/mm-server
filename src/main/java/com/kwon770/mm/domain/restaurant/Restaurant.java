package com.kwon770.mm.domain.restaurant;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kwon770.mm.domain.post.PostImage;
import com.kwon770.mm.domain.restaurant.review.Review;
import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.dto.restaurant.RestaurantRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private Boolean deliverable;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Float averageGrade = 0.0F;

    private String openTime;

    private String closeTime;

    @OneToMany(mappedBy = "restaurant", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RestaurantImage> restaurantImages = new ArrayList<>();

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


    /*
      Relation With Other Entities
     */
    @OneToMany(mappedBy = "restaurant")
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "likedRestaurants")
    @JsonBackReference
    private List<Member> likingMembers = new ArrayList<>();


    @Builder
    public Restaurant(String name, String description,
                      Type type, Price price, Location location,
                      Boolean deliverable, Double latitude, Double longitude,
                      String openTime, String closeTime) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.location = location;
        this.deliverable = deliverable;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public void update(RestaurantRequestDto restaurantRequestDto) {
        this.name = restaurantRequestDto.getName();
        this.description = restaurantRequestDto.getDescription();
        this.type = restaurantRequestDto.getType();
        this.price = restaurantRequestDto.getPrice();
        this.location = restaurantRequestDto.getLocation();
        this.deliverable = restaurantRequestDto.getDeliverable();
        this.latitude = restaurantRequestDto.getLatitude();
        this.longitude = restaurantRequestDto.getLongitude();
        this.openTime = restaurantRequestDto.getOpenTime();
        this.closeTime = restaurantRequestDto.getCloseTime();
    }

    public void updateRestaurantImage(List<RestaurantImage> restaurantImages) {
        this.restaurantImages = restaurantImages;
    }

    public Float getAverageGrade() {
        Double result = Math.ceil(averageGrade * 2) / 2;
        return result.floatValue();
    }

    public boolean isExistImage() {
        return !restaurantImages.isEmpty();
    }

    public int getImagesCount() {
        return restaurantImages.size();
    }

    public List<MultipartFile> getAddedRestaurantImages(List<MultipartFile> newImages) {
        return newImages.stream().filter(image -> {
            for (RestaurantImage postImage : restaurantImages) {
                if (postImage.getOriginalFileName().equals(image.getOriginalFilename())) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }

    public List<RestaurantImage> getRemovedRestaurantImages(List<MultipartFile> newImages) {
        return restaurantImages.stream().filter(image -> {
            for (MultipartFile newImage : newImages) {
                if (newImage.getOriginalFilename().equals(image.getOriginalFileName())) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
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

    public void calculateAddedAverageGrade(float grade) {
        int reviewCount = reviews.size();
        float sum = (averageGrade * reviewCount) + grade;
        averageGrade = sum / (reviewCount + 1);
    }

    public void calculateSubtractedAverageGrade(float grade) {
        int reviewCount = reviews.size();
        if (reviewCount == 1) {
            averageGrade = 0F;
            return;
        }

        float sum = (averageGrade * reviewCount) - grade;
        averageGrade = sum / (reviewCount - 1);
    }

    public void calculateUpdatedAverageGrade(float oldGrade, float newGrade) {
        int reviewCount = reviews.size();
        float sum = (averageGrade * reviewCount) - oldGrade + newGrade;
        averageGrade = sum / reviewCount;
    }
}
