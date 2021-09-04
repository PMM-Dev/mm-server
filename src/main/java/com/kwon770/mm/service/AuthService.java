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
        if (memberRequestDto.getSocialTokenType() == SocialTokenType.GOOGLE) {
            validateGoogleSocialToken(memberRequestDto.getEmail(), memberRequestDto.getSocialToken());
        }
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateJwtTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(jwtTokenDto.getRefreshToken())
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
            throw new IllegalArgumentException("올바르지 않은 socialToken 입니다. socialToken=" + socialToken);
        } catch (Exception e) {
            LogView.logErrorStacktraceWithMessage(e, "알 수 없는 이유로 Google-OAuth로부터 이메일을 요청받지 못했습니다.");
            return "";
        }
    }

    @Transactional
    public JwtTokenDto reissue(JwtTokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new CustomAuthenticationException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new CustomJwtRuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateJwtTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(jwtTokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return jwtTokenDto;
    }
}