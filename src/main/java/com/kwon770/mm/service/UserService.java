package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.domain.user.UserRepository;
import com.kwon770.mm.provider.security.AuthToken;
import com.kwon770.mm.provider.security.JwtAuthTokenProvider;
import com.kwon770.mm.web.dto.UserInfoDto;
import com.kwon770.mm.web.dto.UserSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    private final UserRepository userRepository;

    public Optional<UserInfoDto> login(UserSaveDto userSaveDto) {
        try {
            User user = getUserByEmail(userSaveDto.getEmail());
            return Optional.of(new UserInfoDto(user));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public UserInfoDto registerUser(UserSaveDto userSaveDto) {
        User user = userRepository.save(userSaveDto.toEntity());
        return new UserInfoDto(user);
    }

    public AuthToken createAuthToken(UserInfoDto userInfoDto) {
        return jwtAuthTokenProvider.createAuthToken(userInfoDto.getEmail(), userInfoDto.getRole().getCode());
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findOneById(id).orElseThrow(() -> new IllegalArgumentException("id가 일치하는 유저가 없습니다. id=" + id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("email이 일치하는 유저가 없습니다. email=" + email));
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
