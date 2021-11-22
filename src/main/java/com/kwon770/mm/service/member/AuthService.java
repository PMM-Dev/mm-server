package com.kwon770.mm.service.member;

import com.google.gson.*;
import com.kwon770.mm.domain.member.MemberRepository;
import com.kwon770.mm.domain.member.RefreshToken;
import com.kwon770.mm.domain.member.RefreshTokenRepository;
import com.kwon770.mm.domain.member.SocialTokenType;
import com.kwon770.mm.exception.CustomAuthenticationException;
import com.kwon770.mm.exception.CustomJwtRuntimeException;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.exception.SystemIOException;
import com.kwon770.mm.provider.security.JwtTokenProvider;
import com.kwon770.mm.dto.JwtTokenDto;
import com.kwon770.mm.dto.JwtTokenRequestDto;
import com.kwon770.mm.dto.member.MemberRequestDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
import java.math.BigInteger;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${admin-token-secret}")
    private String adminTokenSecret;
    private final String GOOGLE_USER_INFO_API_URL = "https://www.googleapis.com/userinfo/v2/me";
    private final String APPLE_DECODE_KEY_URL = "https://appleid.apple.com/auth/keys";


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

        if (memberRequestDto.getSocialTokenType().equals(SocialTokenType.APPLE)) {
            memberRequestDto.setAppleEntityValue(getEmailBySocialTokenFromApple(memberRequestDto.getSocialToken()));
        }

        return memberRepository.save(memberRequestDto.toEntity(passwordEncoder)).getId();
    }

    @Transactional
    public Optional<JwtTokenDto> loginByGoogle(MemberRequestDto memberRequestDto) {
        // 관리자 토큰이 아닌 경우, 토큰 유효성 확인
        if (!memberRequestDto.getSocialToken().equals(adminTokenSecret)) {
            String requestTokenEmail = getEmailBySocialTokenFromGoogle(memberRequestDto.getSocialToken());
            validateSocialToken(requestTokenEmail, memberRequestDto.getEmail());

            if (!memberRepository.existsByEmail(requestTokenEmail)) {
                return Optional.empty();
            }
        }

        return issueJwtTokenDto(memberRequestDto);
    }

    private void validateSocialToken(String requestTokenEmail, String requestEmail) {
        if (!requestEmail.equals(requestTokenEmail)) {
            throw new CustomAuthenticationException(ErrorCode.NO_USER_BY_GOOGLE_SOCIAL_TOKEN);
        }
    }

    private String getEmailBySocialTokenFromGoogle(String socialToken) {
        try {
            URL url = new URL(GOOGLE_USER_INFO_API_URL);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + socialToken);

            StringBuffer response = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            String responseBody = response.toString();
            return responseBody.split(",")[1].split("\"")[3];
        } catch (IOException e) {
            throw new SystemIOException();
        } catch (Exception e) {
            throw new CustomAuthenticationException(ErrorCode.WRONG_GOOGLE_SOCIAL_TOKEN);
        }
    }

    @Transactional
    public Optional<JwtTokenDto> loginByApple(MemberRequestDto memberRequestDto) {
        // 관리자 토큰이 아닌 경우, 토큰 유효성 확인
        if (!memberRequestDto.getSocialToken().equals(adminTokenSecret)) {
            String requestTokenEmail = getEmailBySocialTokenFromApple(memberRequestDto.getSocialToken());

            if (!memberRepository.existsByEmail(requestTokenEmail)) {
                return Optional.empty();
            }
        }

        return issueJwtTokenDto(memberRequestDto);
    }

    private String getEmailBySocialTokenFromApple(String socialToken) {
        PublicKey decodePublicKey = getSocialTokenDecodeKeyFromApple(socialToken);

        Claims userInfo = Jwts.parserBuilder()
                .setSigningKey(decodePublicKey)
                .build()
                .parseClaimsJws(socialToken)
                .getBody();
        JsonParser parser = new JsonParser();
        JsonObject userInfoObject = (JsonObject) parser.parse(new Gson().toJson(userInfo));

        return userInfoObject.get("email").getAsString();
    }

    private PublicKey getSocialTokenDecodeKeyFromApple(String socialToken) {
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(APPLE_DECODE_KEY_URL);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
        } catch (IOException e) {
            throw new SystemIOException();
        }

        JsonParser parser = new JsonParser();
        JsonObject keys = (JsonObject) parser.parse(response.toString());
        JsonArray keyArray = (JsonArray) keys.get("keys");

        String[] decodeArray = socialToken.split("\\.");
        String header = new String(Base64.getDecoder().decode(decodeArray[0]));

        JsonElement kid = ((JsonObject) parser.parse(header)).get("kid");
        JsonElement alg = ((JsonObject) parser.parse(header)).get("alg");

        JsonObject decodeKeyObject = null;
        for (JsonElement key : keyArray) {
            JsonElement keyKid = ((JsonObject) key).get("kid");
            JsonElement keyAlg = ((JsonObject) key).get("alg");
            if (kid.equals(keyKid) && alg.equals(keyAlg)) {
                decodeKeyObject = (JsonObject) key;
            }
        }
        if (decodeKeyObject == null) {
            throw new CustomAuthenticationException(ErrorCode.WRONG_APPLE_SOCIAL_TOKEN);
        }

        return getPublicKey(decodeKeyObject);
    }

    private PublicKey getPublicKey(JsonObject decodeKeyObject) {
        String nStr = decodeKeyObject.get("n").toString();
        String eStr = decodeKeyObject.get("e").toString();

        byte[] nBytes = Base64.getUrlDecoder().decode(nStr.substring(1, nStr.length() - 1));
        byte[] eBytes = Base64.getUrlDecoder().decode(eStr.substring(1, eStr.length() - 1));

        BigInteger n = new BigInteger(1, nBytes);
        BigInteger e = new BigInteger(1, eBytes);

        try {
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            return publicKey;
        } catch (Exception exception) {
            throw new CustomAuthenticationException(ErrorCode.WRONG_APPLE_SOCIAL_TOKEN);
        }
    }

    private Optional<JwtTokenDto> issueJwtTokenDto(MemberRequestDto memberRequestDto) {
        // Email 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

        // authenticate 메소드는 CustomUserDetailsService의 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateJwtTokenDto(authentication);

        // RefreshToken 저장
        // authentication.getName() is equal with member id
        RefreshToken refreshToken = RefreshToken.builder()
                .memberId(authentication.getName())
                .tokenKey(jwtTokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        // 토큰 발급
        return Optional.of(jwtTokenDto);
    }

    @Transactional
    public JwtTokenDto reissue(JwtTokenRequestDto tokenRequestDto) {
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new CustomJwtRuntimeException("RefreshToken이 유효하지 않습니다.");
        }

        UsernamePasswordAuthenticationToken authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        RefreshToken refreshToken = refreshTokenRepository.findByTokenKey(tokenRequestDto.getRefreshToken())
                .orElseThrow(() -> new CustomAuthenticationException("해당하는 RefreshToken이 없습니다"));

        // authentication.getName() is equal with member id
        if (!refreshToken.getMemberId().equals(authentication.getName())) {
            throw new CustomJwtRuntimeException("RefreshToken의 유저 정보가 일치하지 않습니다");
        }

        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateJwtTokenDto(authentication);
        RefreshToken newRefreshToken = refreshToken.updateValue(jwtTokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return jwtTokenDto;
    }
}