package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.domain.user.UserRepository;
import com.kwon770.mm.web.dto.UserSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User login(UserSaveDto userSaveDto) {
        if (userRepository.existsByEmail(userSaveDto.getEmail())) {
            return getUserByEmail(userSaveDto.getEmail());
        } else {
            return registerUser(userSaveDto);
        }
    }

    public User registerUser(UserSaveDto userSaveDto) {
        return userRepository.save(userSaveDto.toEntity());
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
