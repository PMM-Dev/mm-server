package com.kwon770.mm.web;

import com.kwon770.mm.service.AuthService;
import com.kwon770.mm.service.MemberService;
import com.kwon770.mm.web.dto.JwtTokenDto;
import com.kwon770.mm.web.dto.JwtTokenRequestDto;
import com.kwon770.mm.web.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        JwtTokenDto jwtTokenDto = authService.login(memberRequestDto);

        return new ResponseEntity<>(jwtTokenDto, HttpStatus.OK);
    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<JwtTokenDto> reissue(@RequestBody JwtTokenRequestDto jwtTokenRequestDto) {
        JwtTokenDto jwtTokenDto = authService.reissue(jwtTokenRequestDto);

        return new ResponseEntity<>(jwtTokenDto, HttpStatus.OK);
    }
}
