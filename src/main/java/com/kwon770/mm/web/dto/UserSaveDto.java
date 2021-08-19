package com.kwon770.mm.web.dto;

import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.provider.security.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveDto {

    private String name;
    private String email;
    private String picture;
    private Role role;

    @Builder
    public UserSaveDto(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(role)
                .build();
    }
}
