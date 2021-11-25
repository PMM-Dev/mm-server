package com.kwon770.mm.service;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.member.MemberRepository;
import com.kwon770.mm.domain.post.Post;
import com.kwon770.mm.domain.post.PostRepository;
import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.RestaurantRepository;
import com.kwon770.mm.dto.post.PostElementDto;
import com.kwon770.mm.dto.restaurant.RestaurantElementDto;
import com.kwon770.mm.service.member.MemberMapper;
import com.kwon770.mm.service.restaurant.RestaurantMapper;
import com.kwon770.mm.dto.member.MemberSearchDto;
import com.kwon770.mm.dto.restaurant.RestaurantSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public List<RestaurantElementDto> searchRestaurantByKeyword(String keyword) {
        List<Restaurant> searchedRestaurants = restaurantRepository.findTop10ByNameContaining(keyword);

        return RestaurantMapper.INSTANCE.restaurantsToRestaurantElementDtos(searchedRestaurants);
    }

    public List<MemberSearchDto> searchMemberByKeyword(String keyword) {
        List<Member> searchedMembers = memberRepository.findTop10ByNameContainingOrEmailContaining(keyword);

        return MemberMapper.INSTANCE.membersToMemberSearchDtos(searchedMembers);
    }

    public List<PostElementDto> searchPostByKeyword(String keyword) {
        List<Post> searchedPosts = postRepository.findTop10ByTitleContaining(keyword);

        return searchedPosts.stream().map(PostElementDto::new).collect(Collectors.toList());
    }
}
