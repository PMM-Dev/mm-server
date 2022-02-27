package com.kwon770.mm.service.restaurant;

import com.kwon770.mm.domain.restaurant.*;
import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.service.member.MemberService;
import com.kwon770.mm.dto.restaurant.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;
    private final RestaurantThemeService restaurantThemeService;
    private final MemberService memberService;


    public Long save(RestaurantRequestDto restaurantRequestDto) {
        return restaurantRepository.save(restaurantRequestDto.toEntity()).getId();
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantByRestaurantId(Long id) {
        return restaurantRepository.findOneById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_RESTAURANT_BY_RESTAURANTID + id));
    }

    public RestaurantInfoDto getRestaurantInfoDtoById(Long id) {
        Restaurant restaurant = getRestaurantByRestaurantId(id);

        return new RestaurantInfoDto(restaurant);
    }

    public Restaurant getRestaurantByName(String name) {
        return restaurantRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_RESTAURANT_BY_RESTAURANTNAME + name));
    }

    public RestaurantInfoDto getRestaurantInfoDtoByName(String name) {
        Restaurant restaurant = getRestaurantByName(name);

        return new RestaurantInfoDto(restaurant);
    }

    public List<RestaurantElementDto> getRestaurantElementDtosByType(String type) {
        List<Restaurant> restaurants = restaurantRepository.findAllByType(Type.valueOf(type));

        return restaurants.stream().map(RestaurantElementDto::new).collect(Collectors.toList());
    }

    public List<RestaurantElementDto> getRestaurantElementDtosByTypeOrderByPriceDesc(String type) {
        List<Restaurant> restaurants = restaurantRepository.findAllByTypeOrderByPriceDesc(Type.valueOf(type));

        return restaurants.stream().map(RestaurantElementDto::new).collect(Collectors.toList());
    }

    public List<RestaurantElementDto> getRestaurantElementDtosByTypeOrderByPriceAsc(String type) {
        List<Restaurant> restaurants = restaurantRepository.findAllByTypeOrderByPriceAsc(Type.valueOf(type));

        return restaurants.stream().map(RestaurantElementDto::new).collect(Collectors.toList());
    }

    public List<RestaurantElementDto> getRestaurantElementDtosByTypeOrderByAverageGradeDesc(String type) {
        List<Restaurant> restaurants = restaurantRepository.findAllByTypeOrderByAverageGradeDesc(Type.valueOf(type));

        return restaurants.stream().map(RestaurantElementDto::new).collect(Collectors.toList());
    }

    public List<RestaurantElementDto> getRestaurantElementDtosByTypeOrderByReviewCountDesc(String type) {
        List<Restaurant> restaurants = restaurantQueryRepository.findAllByTypeOrderByReviewCountDesc(type);

        return restaurants.stream().map(RestaurantElementDto::new).collect(Collectors.toList());
    }

    public List<RestaurantElementDto> getRestaurantElementDtosByTypeOrderByLikeCountDesc(String type) {
        List<Restaurant> restaurants = restaurantQueryRepository.findAllByTypeOrderByLikeCountDesc(type);

        return restaurants.stream().map(RestaurantElementDto::new).collect(Collectors.toList());
    }

    public List<RestaurantElementDto> getRestaurantElementDtosByDeliverable() {
        List<Restaurant> restaurants = restaurantRepository.findAllByDeliverableTrue();

        return restaurants.stream().map(RestaurantElementDto::new).collect(Collectors.toList());
    }

    public List<RestaurantElementDto> getRestaurantElementDtosByRank() {
        List<Restaurant> restaurants = restaurantRepository.findTop20ByOrderByAverageGradeDesc();

        return restaurants.stream().map(RestaurantElementDto::new).collect(Collectors.toList());
    }

    public Optional<RestaurantGachaDto> getRestaurantGachaDtoByMultipleCondition(List<String> type, List<String> price, List<String> location, Boolean deliverable) {
        Optional<Restaurant> restaurant = restaurantQueryRepository.findByMultipleConditions(type, price, location, deliverable);
        if (restaurant.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new RestaurantGachaDto(restaurant.get()));
    }

    public List<RestaurantLocationDto> getAllRestaurantLocationDtos() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        return restaurants.stream().map(RestaurantLocationDto::new).collect(Collectors.toList());
    }

    public List<RestaurantThemeDto> getRestaurantThemeDtosByTheme(String theme) {
        RestaurantTheme restaurantTheme = restaurantThemeService.getRestaurantThemeByTheme(theme);
        List<Restaurant> restaurants = restaurantRepository.findAllByThemesContaining(restaurantTheme);

        return restaurants.stream().map(RestaurantThemeDto::new).collect(Collectors.toList());
    }

    @Transactional
    public Long updateRestaurant(Long restaurantId, RestaurantRequestDto restaurantRequestDto) {
        Restaurant restaurant = getRestaurantByRestaurantId(restaurantId);
        restaurant.update(restaurantRequestDto);

        return restaurant.getId();
    }

    @Transactional
    public void likeRestaurant(Long restaurantId) {
        Member member = memberService.getMeById();
        Restaurant restaurant = getRestaurantByRestaurantId(restaurantId);
        member.appendLikedRestaurant(restaurant);
    }

    @Transactional
    public void unlikeRestaurant(Long restaurantId) {
        Member member = memberService.getMeById();
        Restaurant restaurant = getRestaurantByRestaurantId(restaurantId);
        member.subtractedLikedRestaurant(restaurant);
    }

    @Transactional
    public void deleteRestaurantById(Long restaurantId) {
        Restaurant restaurant = getRestaurantByRestaurantId(restaurantId);
        restaurant.removeAllMemberLikeConnection();
        restaurantRepository.delete(restaurant);
    }

    @Transactional
    public void deleteRestaurantByName(String name) {
        Restaurant restaurant = getRestaurantByName(name);
        restaurant.removeAllMemberLikeConnection();
        restaurantRepository.delete(restaurant);
    }
}
