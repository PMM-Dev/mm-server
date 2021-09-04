package com.kwon770.mm.web;

import com.kwon770.mm.service.MemberPropertyService;
import com.kwon770.mm.web.dto.LikedRestaurantDto;
import com.kwon770.mm.web.dto.MemberTitleSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberPropertyApiController {

    private final MemberPropertyService memberPropertyService;

    @PostMapping("/title")
    public Long saveTitle(@RequestBody MemberTitleSaveDto memberTitleSaveDto) {
        return memberPropertyService.saveTitle(memberTitleSaveDto);
    }

    @DeleteMapping("/title/{title}")
    public void deleteTitle(@PathVariable String title) { memberPropertyService.deleteTitle(title); }

    @PutMapping("/member/{email}/title/{title}")
    public void appendTitle(@PathVariable String email, @PathVariable String title) {
        memberPropertyService.appendTitle(email, title);
    }

    @DeleteMapping("/member/{email}/title/{title}")
    public void subtractTitle(@PathVariable String email, @PathVariable String title) {
        memberPropertyService.subtractTitle(email, title);
    }

    @GetMapping("/member/{email}/like")
    public List<LikedRestaurantDto> getLikedRestaurantList(@PathVariable String email) {
        return memberPropertyService.getLikedRestaurantList(email);
    }

    @PutMapping("/member/{email}/like/{restaurantId}")
    public void appendLikedRestaurant(@PathVariable String email, @PathVariable Long restaurantId) {
        memberPropertyService.appendLikedRestaurant(email, restaurantId);
    }

    @DeleteMapping("/member/{email}/like/{restaurantId}")
    public void subtractLikedRestaurant(@PathVariable String email, @PathVariable Long restaurantId) {
        memberPropertyService.subtractedLikedRestaurant(email, restaurantId);
    }
}
