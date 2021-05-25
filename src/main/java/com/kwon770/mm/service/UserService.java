package com.kwon770.mm.service;

import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.domain.user.UserRepository;
import com.kwon770.mm.web.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User login(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            return userRepository.findByEmail(userRequestDto.getEmail());
        } else {
            return register(userRequestDto);
        }
    }

    public User register(UserRequestDto userRequestDto) {
        return userRepository.save(userRequestDto.toEntity());
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findOneById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUserById(Long id) {
        userRepository.delete(getUserById(id));
    }

    public void deleteUserByEmail(String email) {
        userRepository.delete(getUserByEmail(email));
    }
}
