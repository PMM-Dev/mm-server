package com.kwon770.mm.web;

import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.service.UserService;
import com.kwon770.mm.web.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/login")
    public User login (@RequestBody UserRequestDto userRequestDto) {
        return userService.login(userRequestDto);
    }
}
