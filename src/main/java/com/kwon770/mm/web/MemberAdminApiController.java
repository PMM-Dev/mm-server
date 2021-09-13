package com.kwon770.mm.web;

import com.kwon770.mm.service.MemberService;
import com.kwon770.mm.web.dto.MemberInfoDto;
import com.kwon770.mm.web.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kwon770.mm.Utility.isDigit;

@RequiredArgsConstructor
@RestController
public class MemberAdminApiController {

    private final MemberService memberService;

    @GetMapping("/member/list")
    public List<MemberInfoDto> getMemberInfoDtoList() {
        return memberService.getMemberInfoDtoList();
    }

    @PutMapping("/member/{userId}")
    public void updateMemberByEmail(@PathVariable Long userId, @RequestBody MemberRequestDto memberRequestDto) {
        memberService.updateMemberByEmail(userId, memberRequestDto);
    }

    @DeleteMapping("/member/{identifier}")
    public void deleteMemberByIdentifier(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            memberService.deleteMemberById(Long.parseLong(identifier));
        } else {
            memberService.deleteMemberByEmail(identifier);
        }
    }
}
