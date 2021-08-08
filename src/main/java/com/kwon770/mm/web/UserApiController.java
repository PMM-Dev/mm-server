package com.kwon770.mm.web;

import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.service.UserService;
import com.kwon770.mm.web.dto.UserSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kwon770.mm.Utility.isDigit;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @GetMapping("/api/login")
    public User login(@RequestBody UserSaveDto userSaveDto) {
        return userService.login(userSaveDto);
    }

    @PostMapping("/api/user")
    public Long registerUser(@RequestBody UserSaveDto userSaveDto) {
        return userService.registerUser(userSaveDto).getId();
    }

    @GetMapping("/api/user")
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @GetMapping("/api/user/{identifier}")
    public User getUserByIdentifer(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            return userService.getUserById(Long.parseLong(identifier));
        } else {
            return userService.getUserByEmail(identifier);
        }
    }

    @PutMapping("/api/user/{email}")
    public void updateUserByEmail(@PathVariable String email, @RequestBody UserSaveDto userSaveDto) {
        userService.updateUserByEmail(email, userSaveDto);
    }

    @DeleteMapping("/api/user/{identifier}")
    public void deleteUserByIdentifier(@PathVariable String identifier) {
        if (isDigit(identifier)) {
            userService.deleteUserById(Long.parseLong(identifier));
        } else {
            userService.deleteUserByEmail(identifier);
        }
    }


}
