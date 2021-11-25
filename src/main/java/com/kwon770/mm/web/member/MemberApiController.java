package com.kwon770.mm.web.member;

import com.kwon770.mm.dto.member.MemberInfoDto;
import com.kwon770.mm.dto.member.MemberUpdateDto;
import com.kwon770.mm.service.member.MemberPropertyService;
import com.kwon770.mm.service.member.MemberService;
import com.kwon770.mm.service.restaurant.ReviewService;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.dto.restaurant.MyReviewDto;
import com.kwon770.mm.dto.restaurant.RestaurantElementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kwon770.mm.Utility.isDigit;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;
    private final MemberPropertyService memberPropertyService;
    private final ReviewService reviewService;

    @GetMapping("/member")
    public ResponseEntity<MemberInfoDto> getMyMemberInfoDto() {
        MemberInfoDto memberInfoDto = memberService.getMyInfoDto();

        return new ResponseEntity<>(memberInfoDto, HttpStatus.OK);
    }

    @GetMapping("/member/{identifier}")
    public ResponseEntity<MemberInfoDto> getMemberInfoDtoByIdentifier(@PathVariable String identifier) {
        MemberInfoDto memberInfoDto;
        if (isDigit(identifier)) {
            memberInfoDto = memberService.getMemberInfoDtoById(Long.parseLong(identifier));
        } else {
            memberInfoDto = memberService.getMemberInfoDtoByEmail(identifier);
        }

        return new ResponseEntity<>(memberInfoDto, HttpStatus.OK);
    }

    @PutMapping("/member")
    public ResponseEntity<Void> updateMyMember(@RequestBody MemberUpdateDto memberUpdateDto) {
        memberService.updateMemberByUserId(SecurityUtil.getCurrentMemberId(), memberUpdateDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/member/like")
    public ResponseEntity<List<RestaurantElementDto>> getMyLikedRestaurantList() {
        List<RestaurantElementDto> restaurantElementDtos = memberPropertyService.getLikedRestaurantList(SecurityUtil.getCurrentMemberId());

        return new ResponseEntity<>(restaurantElementDtos, HttpStatus.OK);
    }

    @GetMapping("/member/review")
    public ResponseEntity<List<MyReviewDto>> getMyReviewList() {
        List<MyReviewDto> myReviewDtos = reviewService.getMyReviewList(SecurityUtil.getCurrentMemberId());

        return new ResponseEntity<>(myReviewDtos, HttpStatus.OK);
    }


}
