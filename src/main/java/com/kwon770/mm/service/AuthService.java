package com.kwon770.mm.service;

import com.kwon770.mm.domain.member.MemberRepository;
import com.kwon770.mm.domain.member.RefreshToken;
import com.kwon770.mm.domain.member.RefreshTokenRepository;
import com.kwon770.mm.domain.member.SocialTokenType;
import com.kwon770.mm.exception.CustomAuthenticationException;
import com.kwon770.mm.exception.CustomJwtRuntimeException;
import com.kwon770.mm.provider.security.JwtTokenProvider;
import com.kwon770.mm.view.LogView;
import com.kwon770.mm.web.dto.JwtTokenDto;
import com.kwon770.mm.web.dto.JwtTokenRequestDto;
import com.kwon770.mm.web.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${admin-token-secret}")
    private String adminTokenSecret;

    @Transactional
    public Long register(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        return memberRepository.save(memberRequestDto.toEntity(passwordEncoder)).getId();
    }

    @Transactional
    public JwtTokenDto login(MemberRequestDto memberRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        if (!memberRequestDto.getSocialToken().equals(adminTokenSecret)) {
            if (memberRequestDto.getSocialTokenType() == SocialTokenType.GOOGLE) {
                validateGoogleSocialToken(memberRequestDto.getEmail(), memberRequestDto.getSocialToken());
            }
        }
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateJwtTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .memberId(authentication.getName())
                .key(jwtTokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return jwtTokenDto;
    }

    private void validateGoogleSocialToken(String requestEmail, String requestToken) {
        String requestTokenEmail = getEmailBySocialTokenFromGoogle(requestToken);
        if (!requestEmail.equals(requestTokenEmail) || !memberRepository.existsByEmail(requestTokenEmail)) {
            throw new CustomAuthenticationException("올바르지 않은 Social(Google) Access Token 입니다.");
        }
    }

    private String getEmailBySocialTokenFromGoogle(String socialToken) {
        try {
            URL url = new URL("https://www.googleapis.com/userinfo/v2/me");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + socialToken);

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
            throw new CustomAuthenticationException("올바르지 않은 socialToken 입니다. socialToken=" + socialToken);
        } catch (Exception e) {
            LogView.logErrorStacktraceWithMessage(e, "알 수 없는 이유로 Google-OAuth로부터 이메일을 요청받지 못했습니다.");
            return "";
        }
    }

    @Transactional
    public JwtTokenDto reissue(JwtTokenRequestDto tokenRequestDto) {
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("RefreshToken이 유효하지 않습니다.");
        }

        UsernamePasswordAuthenticationToken authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        RefreshToken refreshToken = refreshTokenRepository.findByKey(tokenRequestDto.getRefreshToken())
                .orElseThrow(() -> new CustomAuthenticationException("해당하는 RefreshToken이 없습니다"));
        // authentication.getName() = member id
        if (!refreshToken.getMemberId().equals(authentication.getName())) {
            throw new CustomJwtRuntimeException("RefreshToken의 유저 정보가 일치하지 않습니다");
        }

        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateJwtTokenDto(authentication);
        RefreshToken newRefreshToken = refreshToken.updateValue(jwtTokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return jwtTokenDto;
    }
}