package com.kwon770.mm.web;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.service.MemberService;
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
    public List<Member> getAllMemberList() {
        return memberService.getAllMembers();
    }

    @PutMapping("/member/{userId}")
    public void updateMemberByEmail(@PathVariable Long userId, @RequestBody MemberRequestDto memberRequestDto) {
        memberService.updateMemberByEmail(userId, memberRequestDto);
    }

    @DeleteMapping("/member/{identifier}")
    public boolean deleteMemberByIdentifier(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            memberService.deleteMemberById(Long.parseLong(identifier));
        } else {
            memberService.deleteMemberByEmail(identifier);
        }
        return true;
    }
}
