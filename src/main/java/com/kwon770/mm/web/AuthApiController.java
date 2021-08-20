package com.kwon770.mm.web;

import com.kwon770.mm.provider.security.JwtAuthToken;
import com.kwon770.mm.service.UserService;
import com.kwon770.mm.web.dto.UserInfoDto;
import com.kwon770.mm.web.dto.UserSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthApiController {

    private final UserService userService;

    @GetMapping("/api/login/{jwtToken}")
    public UserInfoDto loginByJwtToken(@PathVariable String jwtToken) {
        JwtAuthToken authToken = userService.getAuthTokenByJwtToken(jwtToken);

        String userEmail = authToken.getUserEmail();
        return userService.getUserInfoDtoByEmail(userEmail);
    }

    @GetMapping("/api/auth/{socialToken}")
    public String getJwtTokenBySocialToken(@PathVariable String socialToken) {
        UserInfoDto userinfoDto = userService.getUserInfoDtoBySocialToken(socialToken);

        JwtAuthToken jwtAuthToken = userService.createAuthToken(userinfoDto);
        return jwtAuthToken.getToken();
    }

    @PostMapping("/api/register")
    public Long register(@RequestBody UserSaveDto userSaveDto) {
        return userService.register(userSaveDto);
    }
}
