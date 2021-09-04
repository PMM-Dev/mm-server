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


    public MemberInfoDto getMemberInfoDtoBySocialToken(String socialToken) {
        String email = getEmailBySocialTokenFromGoogle(socialToken);
        return getMemberInfoDtoByEmail(email);
    }

    private String getEmailBySocialTokenFromGoogle(String socialToken) {
        try {
            URL url = new URL("https://www.googleapis.com/userinfo/v2/me");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + socialToken);

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String responseBody = response.toString();
            return responseBody.split(",")[1].split("\"")[3];
        } catch (IOException e) {
            throw new IllegalArgumentException("올바르지 않은 socialToken 입니다. socialToken=" + socialToken);
        } catch (Exception e) {
            LogView.logErrorStacktraceWithMessage(e, "알 수 없는 이유로 Google-OAuth로부터 이메일을 요청받지 못했습니다.");
            return "";
        }
    }

    public List<MemberInfoDto> getMemberInfoDtoList() {
        List<Member> members = memberRepository.findAll();

        return MemberMapper.INSTANCE.membersToMemberInfoDtos(members);
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

    public void updateMemberByEmail(String email, MemberRequestDto memberRequestDto) {
        Member member = getMemberByEmail(email);
        member.update(memberRequestDto);
    }

    public void deleteMemberById(Long id) {
        memberRepository.delete(getMemberById(id));
    }

    public void deleteMemberByEmail(String email) {
        memberRepository.delete(getMemberByEmail(email));
    }
}
