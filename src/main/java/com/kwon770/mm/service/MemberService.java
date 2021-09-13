package com.kwon770.mm.service;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.member.MemberRepository;
import com.kwon770.mm.provider.security.JwtTokenProvider;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.view.LogView;
import com.kwon770.mm.web.dto.MemberInfoDto;
import com.kwon770.mm.web.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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
        return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id가 일치하는 유저가 없습니다. id=" + id));
    }

    public MemberInfoDto getMemberInfoDtoById(Long id) {
        return MemberMapper.INSTANCE.memberToMemberInfoDto(getMemberById(id));
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("email이 일치하는 유저가 없습니다. email=" + email));
    }

    public MemberInfoDto getMemberInfoDtoByEmail(String email) {
        return MemberMapper.INSTANCE.memberToMemberInfoDto(getMemberByEmail(email));
    }

    public void updateMemberByEmail(Long userId, MemberRequestDto memberRequestDto) {
        Member member = getMemberById(userId);
        member.update(memberRequestDto);
    }

    public void deleteMemberById(Long id) {
        memberRepository.delete(getMemberById(id));
    }

    public void deleteMemberByEmail(String email) {
        memberRepository.delete(getMemberByEmail(email));
    }
}
