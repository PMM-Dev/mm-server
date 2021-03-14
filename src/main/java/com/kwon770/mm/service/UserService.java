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

    public User save(UserRequestDto userRequestDto) {
        return userRepository.save(userRequestDto.toEntity());
    }

    public User login(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            return userRepository.findByEmail(userRequestDto.getEmail());
        } else {
            return save(userRequestDto);
        }
    }
}
