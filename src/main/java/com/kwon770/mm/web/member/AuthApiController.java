package com.kwon770.mm.web.member;

import com.kwon770.mm.domain.member.SocialTokenType;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.service.member.AuthService;
import com.kwon770.mm.dto.JwtTokenDto;
import com.kwon770.mm.dto.JwtTokenRequestDto;
import com.kwon770.mm.dto.member.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/auth/register")
    public ResponseEntity<Long> register(@RequestBody MemberRequestDto memberRequestDto) {
        Long memberId = authService.register(memberRequestDto);

        return new ResponseEntity<>(memberId, HttpStatus.OK);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        if (memberRequestDto.getSocialTokenType() == null) {
            throw new IllegalArgumentException(ErrorCode.SOCIAL_TOKEN_TYPE_NOT_EXIST);
        }

        System.out.println(memberRequestDto.getSocialTokenType());

        Optional<JwtTokenDto> jwtTokenDto = Optional.empty();
        if (memberRequestDto.getSocialTokenType().equals(SocialTokenType.GOOGLE)) {
            jwtTokenDto = authService.loginByGoogle(memberRequestDto);

        } else if (memberRequestDto.getSocialTokenType().equals(SocialTokenType.APPLE)) {
            jwtTokenDto = authService.loginByApple(memberRequestDto);
        }


        if (jwtTokenDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(jwtTokenDto.get(), HttpStatus.OK);
        }
    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<JwtTokenDto> reissue(@RequestBody JwtTokenRequestDto jwtTokenRequestDto) {
        JwtTokenDto jwtTokenDto = authService.reissue(jwtTokenRequestDto);

        return new ResponseEntity<>(jwtTokenDto, HttpStatus.OK);
    }
}
