package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.user.Title;
import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.domain.user.UserTitle;
import com.kwon770.mm.domain.user.UserTitleRepository;
import com.kwon770.mm.web.dto.LikedRestaurantDto;
import com.kwon770.mm.web.dto.RestaurantMapper;
import com.kwon770.mm.web.dto.UserTitleSaveDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserPropertyService{

    private final UserService userService;
    private final UserTitleRepository userTitleRepository;

    private final RestaurantService restaurantService;

    public Long saveTitle(UserTitleSaveDto userTitleSaveDto) {
        return userTitleRepository.save(userTitleSaveDto.toEntity()).getId();
    }

    public void deleteTitle(String title) {
        userTitleRepository.delete(userTitleRepository.findByTitle(Title.valueOf(title)));
    }

    @Transactional
    public void appendTitle(String email, String title) {
        User user = userService.getUserByEmail(email);
        UserTitle userTitle = userTitleRepository.findByTitle(Title.valueOf(title));

        user.appendTitle(userTitle);
    }

    @Transactional
    public void subtractTitle(String email, String title) {
        User user = userService.getUserByEmail(email);
        UserTitle userTitle = userTitleRepository.findByTitle(Title.valueOf(title));

        user.subtractTitle(userTitle);
    }

    public List<LikedRestaurantDto> getLikedRestaurantList(String email) {
        User user = userService.getUserByEmail(email);
        List<Restaurant> likedRestaurantEntities = user.getLikedRestaurants();

        return RestaurantMapper.INSTANCE.map(likedRestaurantEntities);
    }

    @Transactional
    public void appendLikedRestaurant(String email, Long restaurantId) {
        User user = userService.getUserByEmail(email);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        user.appendLikedRestaurant(restaurant);
    }

    @Transactional
    public void subtractedLikedRestaurant(String email, Long restaurantId) {
        User user = userService.getUserByEmail(email);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        user.subtractedLikedRestaurant(restaurant);
    }
}
