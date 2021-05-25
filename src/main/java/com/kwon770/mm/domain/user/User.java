package com.kwon770.mm.domain.user;

import com.kwon770.mm.web.dto.UserSaveDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String picture;

    @Builder
    public User(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public void update(UserSaveDto userSaveDto) {
        this.name = userSaveDto.getName();
        this.email = userSaveDto.getEmail();
        this.picture = userSaveDto.getPicture();
    }
}
