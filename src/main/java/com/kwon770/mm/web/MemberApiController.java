package com.kwon770.mm.web;

import com.kwon770.mm.service.MemberPropertyService;
import com.kwon770.mm.service.MemberService;
import com.kwon770.mm.service.RestaurantService;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.web.dto.MemberInfoDto;
import com.kwon770.mm.web.dto.MemberRequestDto;
import com.kwon770.mm.web.dto.Restaurant.MyReviewDto;
import com.kwon770.mm.web.dto.Restaurant.RestaurantElementDto;
import com.kwon770.mm.web.dto.Restaurant.ReviewInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kwon770.mm.Utility.isDigit;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;
    private final MemberPropertyService memberPropertyService;
    private final RestaurantService restaurantService;

    @GetMapping("/member/me")
    public MemberInfoDto getMyMemberInfo() {
        return memberService.getMyInfoDto();
    }

    @GetMapping("/member/{identifier}")
    public MemberInfoDto getMemberByIdentifier(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            return memberService.getMemberInfoDtoById(Long.parseLong(identifier));
        } else {
            return memberService.getMemberInfoDtoByEmail(identifier);
        }
    }

    @PutMapping("/member/me")
    public void updateMemberByEmail(@RequestBody MemberRequestDto memberRequestDto) {
        memberService.updateMemberByEmail(SecurityUtil.getCurrentMemberId(), memberRequestDto);
    }

    @GetMapping("/member/me/like")
    public List<RestaurantElementDto> getMyLikedRestaurantList() {
        return memberPropertyService.getLikedRestaurantList(SecurityUtil.getCurrentMemberId());
    }

    @PutMapping("/member/like/{restaurantId}")
    public void appendLikedRestaurant(@PathVariable Long restaurantId) {
        memberPropertyService.appendLikedRestaurant(restaurantId);
    }

    @DeleteMapping("/member/like/{restaurantId}")
    public boolean subtractLikedRestaurant(@PathVariable Long restaurantId) {
        memberPropertyService.subtractedLikedRestaurant(restaurantId);
        return true;
    }

    @GetMapping("/member/me/review")
    public List<MyReviewDto> getMyReviewList() {
        return restaurantService.getMyReviewList(SecurityUtil.getCurrentMemberId());
    }
}
