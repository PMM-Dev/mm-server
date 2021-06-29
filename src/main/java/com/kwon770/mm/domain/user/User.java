package com.kwon770.mm.domain.user;

import com.kwon770.mm.web.dto.UserSaveDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "user_title_relation",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_title_id")
    )
    private List<UserTitle> titles = new ArrayList<>();

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

    public void appendTitle(UserTitle userTitle) { this.titles.add(userTitle); }

    public void subtractTitle(UserTitle userTitle) { this.titles.remove(userTitle); }
}
