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
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/member/me")
    public MemberInfoDto getMyMemberInfo() {
        return memberService.getMyInfoDto();
    }

    @GetMapping("/api/member")
    public List<MemberInfoDto> getMemberInfoDtoList() {
        return memberService.getMemberInfoDtoList();
    }

    @GetMapping("/api/member/{identifier}")
    public MemberInfoDto getMemberByIdentifer(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            return memberService.getMemberInfoDtoById(Long.parseLong(identifier));
        } else {
            return memberService.getMemberInfoDtoByEmail(identifier);
        }
    }

    @PutMapping("/api/member/{email}")
    public void updateMemberByEmail(@PathVariable String email, @RequestBody MemberRequestDto memberRequestDto) {
        memberService.updateMemberByEmail(email, memberRequestDto);
    }

    @DeleteMapping("/api/member/{identifier}")
    public void deleteMemberByIdentifier(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            memberService.deleteMemberById(Long.parseLong(identifier));
        } else {
            memberService.deleteMemberByEmail(identifier);
        }
    }


}
