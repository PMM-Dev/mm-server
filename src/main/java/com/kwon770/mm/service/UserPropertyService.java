package com.kwon770.mm.service;

import com.kwon770.mm.domain.user.Title;
import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.domain.user.UserTitle;
import com.kwon770.mm.domain.user.UserTitleRepository;
import com.kwon770.mm.web.dto.UserTitleSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserPropertyService {

    private final UserService userService;
    private final UserTitleRepository userTitleRepository;

    public Long saveTitle(UserTitleSaveDto userTitleSaveDto) {
        return userTitleRepository.save(userTitleSaveDto.toEntity()).getId();
    }

    public void deleteTitle(String title) {
        userTitleRepository.delete(userTitleRepository.findByTitle(Title.valueOf(title)));
    }

    @Transactional
    public void appendTitle(String email, String title) {
        User user = userService.getUserByEmail(email);
        UserTitle userTitle = userTitleRepository.findByTitle(Title.valueOf(title));

        user.appendTitle(userTitle);
    }

    @Transactional
    public void subtractTitle(String email, String title) {
        User user = userService.getUserByEmail(email);
        UserTitle userTitle = userTitleRepository.findByTitle(Title.valueOf(title));

        user.subtractTitle(userTitle);
    }
}
