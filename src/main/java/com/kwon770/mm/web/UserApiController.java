package com.kwon770.mm.web;

import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.provider.security.JwtAuthToken;
import com.kwon770.mm.service.UserService;
import com.kwon770.mm.web.dto.UserInfoDto;
import com.kwon770.mm.web.dto.UserSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.kwon770.mm.Utility.isDigit;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @GetMapping("/api/login")
    public UserInfoDto login(@RequestBody UserSaveDto userSaveDto) {
        UserInfoDto userinfoDto;
        Optional<UserInfoDto> presentUserInfoDto = userService.login(userSaveDto);
        if (presentUserInfoDto.isPresent()) {
            userinfoDto = presentUserInfoDto.get();
        } else {
            userinfoDto = userService.registerUser(userSaveDto);
        }

        JwtAuthToken jwtAuthToken = (JwtAuthToken) userService.createAuthToken(userinfoDto);
        userinfoDto.setJwt(jwtAuthToken.getToken());
        return userinfoDto;
    }

    @PostMapping("/api/register")
    public UserInfoDto registerUser(@RequestBody UserSaveDto userSaveDto) {
        return userService.registerUser(userSaveDto);
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
