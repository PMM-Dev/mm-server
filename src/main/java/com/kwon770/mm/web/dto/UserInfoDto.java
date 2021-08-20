package com.kwon770.mm.web.dto;

import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.provider.security.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {

    private String name;
    private String email;
    private String picture;
    private Role role;

    public UserInfoDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.role = user.getRole();
    }
}
