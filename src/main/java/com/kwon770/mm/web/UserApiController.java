package com.kwon770.mm.web;

import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.service.UserService;
import com.kwon770.mm.web.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kwon770.mm.Utility.isDigit;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/v1/user/login")
    public User login(@RequestBody UserRequestDto userRequestDto) {
        return userService.login(userRequestDto);
    }

    @PatchMapping("/api/v1/user/save")
    public Long save(@RequestBody UserRequestDto userRequestDto) {
        return userService.save(userRequestDto).getId();
    }

    @GetMapping("/api/v1/user/read/{identifier}")
    public void read(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            userService.findOneById(Long.parseLong(identifier));
        } else {
            userService.findByEmail(identifier);
        }
    }

    @GetMapping("/api/v1/user/list")
    public List<User> readList() {
        return userService.findAll();
    }

    @DeleteMapping("/api/v1/user/delete/{identifier}")
    public void delete(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            userService.deleteById(Long.parseLong(identifier));
        } else {
            userService.deleteByEmail(identifier);
        }
    }
}
