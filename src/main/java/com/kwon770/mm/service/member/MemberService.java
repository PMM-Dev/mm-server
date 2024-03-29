package com.kwon770.mm.service.member;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.member.MemberRepository;
import com.kwon770.mm.dto.member.MemberInfoDto;
import com.kwon770.mm.dto.member.MemberUpdateDto;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberInfoDto getMyInfoDto() {
        return getMemberInfoDtoById(SecurityUtil.getCurrentMemberId());
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_MEMBER_BY_USERID + id));
    }

    public Member getMeById() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_MEMBER_BY_USERID));
    }

    public MemberInfoDto getMemberInfoDtoById(Long id) {
        return new MemberInfoDto(getMemberById(id));
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_MEMBER_BY_EMAIL + email));
    }

    public MemberInfoDto getMemberInfoDtoByEmail(String email) {
        return new MemberInfoDto(getMemberByEmail(email));
    }

    @Transactional
    public void updateMemberByUserId(Long userId, MemberUpdateDto memberUpdateDto) {
        Member member = getMemberById(userId);
        member.update(memberUpdateDto);
    }

    public void deleteMemberById(Long id) {
        memberRepository.delete(getMemberById(id));
    }

    public void deleteMemberByEmail(String email) {
        memberRepository.delete(getMemberByEmail(email));
    }
}
