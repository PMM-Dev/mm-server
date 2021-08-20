package com.kwon770.mm.domain.user;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.provider.security.Role;
import com.kwon770.mm.web.dto.UserSaveDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String picture;

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String socialToken;

    @ManyToMany
    @JoinTable(
            name = "user_title_relation",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_title_id")
    )
    private List<UserTitle> titles = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_restaurant_like_relation",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private List<Restaurant> likedRestaurants = new ArrayList<>();

    @Builder
    public User(String name, String email, String picture, Role role, String socialToken) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.socialToken = socialToken;
    }

    public void update(UserSaveDto userSaveDto) {
        this.name = userSaveDto.getName();
        this.email = userSaveDto.getEmail();
        this.picture = userSaveDto.getPicture();
        this.role = userSaveDto.getRole();
        this.socialToken = userSaveDto.getSocialToken();
    }

    public void appendTitle(UserTitle userTitle) { this.titles.add(userTitle); }

    public void subtractTitle(UserTitle userTitle) { this.titles.remove(userTitle); }

    public void appendLikedRestaurant(Restaurant restaurant) { this.likedRestaurants.add(restaurant); }

    public void subtractedLikedRestaurant(Restaurant restaurant) { this.likedRestaurants.remove(restaurant); }
}
