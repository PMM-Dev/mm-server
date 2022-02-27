package com.kwon770.mm.service.member;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.member.Title;
import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.member.MemberTitle;
import com.kwon770.mm.domain.member.MemberTitleRepository;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.service.FeedbackService;
import com.kwon770.mm.service.restaurant.RestaurantService;
import com.kwon770.mm.dto.member.MemberTitleRequestDto;
import com.kwon770.mm.dto.restaurant.RestaurantElementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberPropertyService {

    private final MemberService memberService;
    private final MemberTitleRepository memberTitleRepository;

    public Long saveTitle(MemberTitleRequestDto memberTitleRequestDto) {
        return memberTitleRepository.save(memberTitleRequestDto.toEntity()).getId();
    }

    public MemberTitle getMemberTitleByTitle(String title) {
        return memberTitleRepository.findByTitle(Title.valueOf(title))
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_MEMBER_TITLE_BY_TITLE + title));
    }

    public void deleteTitle(String title) {
        MemberTitle memberTitle = getMemberTitleByTitle(title);

        memberTitleRepository.delete(memberTitle);
    }

    @Transactional
    public void appendTitle(String title) {
        Member member = memberService.getMeById();
        MemberTitle memberTitle = getMemberTitleByTitle(title);

        member.appendTitle(memberTitle);
    }

    @Transactional
    public void subtractTitle(String title) {
        Member member = memberService.getMeById();
        MemberTitle memberTitle = getMemberTitleByTitle(title);

        member.subtractTitle(memberTitle);
    }

    public List<RestaurantElementDto> getLikedRestaurantList(Long userId) {
        Member member = memberService.getMemberById(userId);
        List<Restaurant> likedRestaurants = member.getLikedRestaurants();

        return likedRestaurants.stream().map(RestaurantElementDto::new).collect(Collectors.toList());
    }
}
