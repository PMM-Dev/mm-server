package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.member.Title;
import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.member.MemberTitle;
import com.kwon770.mm.domain.member.MemberTitleRepository;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.web.dto.MemberTitleRequestDto;
import com.kwon770.mm.web.dto.Restaurant.RestaurantElementDto;
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

    public Long saveTitle(MemberTitleRequestDto memberTitleRequestDto) {
        return memberTitleRepository.save(memberTitleRequestDto.toEntity()).getId();
    }

    public void deleteTitle(String title) {
        memberTitleRepository.delete(memberTitleRepository.findByTitle(Title.valueOf(title)));
    }

    @Transactional
    public void appendTitle(String title) {
        Member member = memberService.getMemberById(SecurityUtil.getCurrentMemberId());
        MemberTitle memberTitle = memberTitleRepository.findByTitle(Title.valueOf(title));

        member.appendTitle(memberTitle);
    }

    @Transactional
    public void subtractTitle(String title) {
        Member member = memberService.getMemberById(SecurityUtil.getCurrentMemberId());
        MemberTitle memberTitle = memberTitleRepository.findByTitle(Title.valueOf(title));

        member.subtractTitle(memberTitle);
    }

    public List<RestaurantElementDto> getLikedRestaurantList(Long userId) {
        Member member = memberService.getMemberById(userId);
        List<Restaurant> likedRestaurantEntities = member.getLikedRestaurants();

        return RestaurantMapper.INSTANCE.restaurantsToRestaurantElementDtos(likedRestaurantEntities);
    }

    @Transactional
    public void appendLikedRestaurant(Long restaurantId) {
        Member member = memberService.getMemberById(SecurityUtil.getCurrentMemberId());
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        restaurant.addLikeCount();
        member.appendLikedRestaurant(restaurant);
    }

    @Transactional
    public void subtractedLikedRestaurant(Long restaurantId) {
        Member member = memberService.getMemberById(SecurityUtil.getCurrentMemberId());
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        restaurant.subtractLikeCount();
        member.subtractedLikedRestaurant(restaurant);
    }
}
