package com.kwon770.mm.web.member;

import com.kwon770.mm.service.member.MemberPropertyService;
import com.kwon770.mm.web.dto.MemberTitleRequestDto;
import com.kwon770.mm.web.dto.Restaurant.RestaurantElementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberPropertyApiController {

    private final MemberPropertyService memberPropertyService;

    @PostMapping("/title")
    public ResponseEntity<Long> saveTitle(@RequestBody MemberTitleRequestDto memberTitleRequestDto) {
        Long titleId = memberPropertyService.saveTitle(memberTitleRequestDto);

        return new ResponseEntity<>(titleId, HttpStatus.OK);
    }

    @DeleteMapping("/title/{title}")
    public ResponseEntity<Void> deleteTitle(@PathVariable String title) {
        memberPropertyService.deleteTitle(title);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/member/title/{title}")
    public ResponseEntity<Void> appendTitle(@PathVariable String title) {
        memberPropertyService.appendTitle(title);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/member/title/{title}")
    public ResponseEntity<Void> subtractTitle(@PathVariable String title) {
        memberPropertyService.subtractTitle(title);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/member/{userId}/like")
    public ResponseEntity<List<RestaurantElementDto>> getLikedRestaurantList(@PathVariable Long userId) {
        List<RestaurantElementDto> restaurantElementDtos = memberPropertyService.getLikedRestaurantList(userId);

        return new ResponseEntity<>(restaurantElementDtos, HttpStatus.OK);
    }
}
