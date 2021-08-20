package com.kwon770.mm.service;

import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.domain.user.UserRepository;
import com.kwon770.mm.provider.security.JwtAuthToken;
import com.kwon770.mm.provider.security.JwtAuthTokenProvider;
import com.kwon770.mm.web.dto.UserInfoDto;
import com.kwon770.mm.web.dto.UserSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    private final UserRepository userRepository;

    public JwtAuthToken getAuthTokenByJwtToken(String jwtToken) {
        return jwtAuthTokenProvider.convertAuthToken(jwtToken);
    }

    public UserInfoDto getUserInfoDtoBySocialToken(String socialToken) {
        User user = userRepository.findBySocialToken(socialToken).orElseThrow(() -> new IllegalArgumentException("socialToken이 일치하는 유저가 없습니다. socialToken=" + socialToken));
        return new UserInfoDto(user);
    }

    public Long register(UserSaveDto userSaveDto) {
        return userRepository.save(userSaveDto.toEntity()).getId();
    }

    public JwtAuthToken createAuthToken(UserInfoDto userInfoDto) {
        return jwtAuthTokenProvider.createAuthToken(userInfoDto.getEmail(), userInfoDto.getRole().getCode());
    }

    // Refactoring with map functions
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id가 일치하는 유저가 없습니다. id=" + id));
    }

    public UserInfoDto getUserInfoDtoById(Long id) {
        return new UserInfoDto(getUserById(id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("email이 일치하는 유저가 없습니다. email=" + email));
    }

    public UserInfoDto getUserInfoDtoByEmail(String email) {
        return new UserInfoDto(getUserByEmail(email));
    }

    public void updateUserByEmail(String email, UserSaveDto userSaveDto) {
        User user = getUserByEmail(email);
        user.update(userSaveDto);
    }

    public void deleteUserById(Long id) {
        userRepository.delete(getUserById(id));
    }

    public void deleteUserByEmail(String email) {
        userRepository.delete(getUserByEmail(email));
    }
}
