package com.kwon770.mm.web;

import com.kwon770.mm.service.MemberPropertyService;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.web.dto.MemberTitleRequestDto;
import com.kwon770.mm.web.dto.Restaurant.RestaurantElementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberPropertyApiController {

    private final MemberPropertyService memberPropertyService;

    @PostMapping("/title")
    public Long saveTitle(@RequestBody MemberTitleRequestDto memberTitleRequestDto) {
        return memberPropertyService.saveTitle(memberTitleRequestDto);
    }

    @DeleteMapping("/title/{title}")
    public void deleteTitle(@PathVariable String title) { memberPropertyService.deleteTitle(title); }

    @PutMapping("/member/title/{title}")
    public void appendTitle(@PathVariable String title) {
        memberPropertyService.appendTitle(title);
    }

    @DeleteMapping("/member/title/{title}")
    public void subtractTitle(@PathVariable String title) {
        memberPropertyService.subtractTitle(title);
    }

    @GetMapping("/member/me/like")
    public List<RestaurantElementDto> getMyLikedRestaurantList() {
        return memberPropertyService.getLikedRestaurantList(SecurityUtil.getCurrentMemberId());
    }

    @GetMapping("/member/{userId}/like")
    public List<RestaurantElementDto> getLikedRestaurantList(@PathVariable Long userId) {
        return memberPropertyService.getLikedRestaurantList(userId);
    }

    @PutMapping("/member/like/{restaurantId}")
    public void appendLikedRestaurant(@PathVariable Long restaurantId) {
        memberPropertyService.appendLikedRestaurant(restaurantId);
    }

    @DeleteMapping("/member/like/{restaurantId}")
    public void subtractLikedRestaurant(@PathVariable Long restaurantId) {
        memberPropertyService.subtractedLikedRestaurant(restaurantId);
    }
}
