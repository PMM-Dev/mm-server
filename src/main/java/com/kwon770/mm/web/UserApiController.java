package com.kwon770.mm.web;

import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.service.UserService;
import com.kwon770.mm.web.dto.UserRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kwon770.mm.Utility.isDigit;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @GetMapping("/api/v1/user")
    public User login(@RequestBody UserRequestDto userRequestDto) {
        return userService.login(userRequestDto);
    }

    @PostMapping("/api/v1/user")
    public Long register(@RequestBody UserRequestDto userRequestDto) {
        return userService.register(userRequestDto).getId();
    }

    @GetMapping("/api/v1/user/list")
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @GetMapping("/api/v1/user/{identifier}")
    public void getUserByIdentifer(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            userService.getUserById(Long.parseLong(identifier));
        } else {
            userService.getUserByEmail(identifier);
        }
    }

    @DeleteMapping("/api/v1/user/delete/{identifier}")
    public void deleteUserByIdentifier(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            userService.deleteUserById(Long.parseLong(identifier));
        } else {
            userService.deleteUserByEmail(identifier);
        }
    }
}
