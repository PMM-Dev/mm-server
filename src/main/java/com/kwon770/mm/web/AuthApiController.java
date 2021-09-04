package com.kwon770.mm.web;

import com.kwon770.mm.service.AuthService;
import com.kwon770.mm.service.MemberService;
import com.kwon770.mm.web.dto.JwtTokenDto;
import com.kwon770.mm.web.dto.JwtTokenRequestDto;
import com.kwon770.mm.web.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/auth/register")
    public Long register(@RequestBody MemberRequestDto memberRequestDto) {
        return authService.register(memberRequestDto);
    }

    @PostMapping("/auth/login")
    public JwtTokenDto login(@RequestBody MemberRequestDto memberRequestDto) {
        return authService.login(memberRequestDto);
    }

    @PostMapping("/auth/reissue")
    public JwtTokenDto reissue(@RequestBody JwtTokenRequestDto jwtTokenRequestDto) {
        return authService.reissue(jwtTokenRequestDto);
    }
}
