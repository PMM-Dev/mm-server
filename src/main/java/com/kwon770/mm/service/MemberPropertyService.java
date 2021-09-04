package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.member.Title;
import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.member.MemberTitle;
import com.kwon770.mm.domain.member.MemberTitleRepository;
import com.kwon770.mm.web.dto.LikedRestaurantDto;
import com.kwon770.mm.web.dto.MemberTitleSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberPropertyService {

    private final MemberService memberService;
    private final MemberTitleRepository memberTitleRepository;

    private final RestaurantService restaurantService;

    public Long saveTitle(MemberTitleSaveDto memberTitleSaveDto) {
        return memberTitleRepository.save(memberTitleSaveDto.toEntity()).getId();
    }

    public void deleteTitle(String title) {
        memberTitleRepository.delete(memberTitleRepository.findByTitle(Title.valueOf(title)));
    }

    @Transactional
    public void appendTitle(String email, String title) {
        Member member = memberService.getMemberByEmail(email);
        MemberTitle memberTitle = memberTitleRepository.findByTitle(Title.valueOf(title));

        member.appendTitle(memberTitle);
    }

    @Transactional
    public void subtractTitle(String email, String title) {
        Member member = memberService.getMemberByEmail(email);
        MemberTitle memberTitle = memberTitleRepository.findByTitle(Title.valueOf(title));

        member.subtractTitle(memberTitle);
    }

    public List<LikedRestaurantDto> getLikedRestaurantList(String email) {
        Member member = memberService.getMemberByEmail(email);
        List<Restaurant> likedRestaurantEntities = member.getLikedRestaurants();

        return RestaurantMapper.INSTANCE.restaurantsToLikedRestaurantDtos(likedRestaurantEntities);
    }

    @Transactional
    public void appendLikedRestaurant(String email, Long restaurantId) {
        Member member = memberService.getMemberByEmail(email);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        restaurant.addLikeCount();
        member.appendLikedRestaurant(restaurant);
    }

    @Transactional
    public void subtractedLikedRestaurant(String email, Long restaurantId) {
        Member member = memberService.getMemberByEmail(email);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        restaurant.subtractLikeCount();
        member.subtractedLikedRestaurant(restaurant);
    }
}
