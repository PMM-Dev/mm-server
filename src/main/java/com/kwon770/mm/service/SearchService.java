package com.kwon770.mm.service;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.member.MemberRepository;
import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.RestaurantRepository;
import com.kwon770.mm.service.member.MemberMapper;
import com.kwon770.mm.service.restaurant.RestaurantMapper;
import com.kwon770.mm.web.dto.MemberSearchDto;
import com.kwon770.mm.web.dto.Restaurant.RestaurantSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;

    public List<RestaurantSearchDto> searchRestaurantByKeyword(String keyword) {
        List<Restaurant> searchedRestaurants = restaurantRepository.findLimit10ByNameContaining(keyword);

        return RestaurantMapper.INSTANCE.restaurantsToRestaurantSearchDtos(searchedRestaurants);
    }

    public List<MemberSearchDto> searchMemberByKeyword(String keyword) {
        List<Member> searchedMembers = memberRepository.findLimit10ByNameContaining(keyword);

        return MemberMapper.INSTANCE.membersToMemberSearchDtos(searchedMembers);
    }
}
