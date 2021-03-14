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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOneById(Long id) {
        return userRepository.findOneById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteById(Long id) {
        userRepository.delete(findOneById(id));
    }

    public void deleteByEmail(String email) {
        userRepository.delete(findByEmail(email));
    }
}
