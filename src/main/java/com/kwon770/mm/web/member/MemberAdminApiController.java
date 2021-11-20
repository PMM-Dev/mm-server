package com.kwon770.mm.web.member;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.service.member.MemberService;
import com.kwon770.mm.dto.member.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kwon770.mm.Utility.isDigit;

@RequiredArgsConstructor
@RestController
public class MemberAdminApiController {

    private final MemberService memberService;

    @GetMapping("/member/list")
    public ResponseEntity<List<Member>> getAllMemberList() {
        List<Member> members = memberService.getAllMembers();

        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @PutMapping("/member/{userId}")
    public ResponseEntity<Void> updateMemberByUserId(@PathVariable Long userId, @RequestBody MemberRequestDto memberRequestDto) {
        memberService.updateMemberByUserId(userId, memberRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/member/{identifier}")
    public ResponseEntity<Void> deleteMemberByIdentifier(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            memberService.deleteMemberById(Long.parseLong(identifier));
        } else {
            memberService.deleteMemberByEmail(identifier);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
