package com.kwon770.mm.web.dto;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.member.Role;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class MemberInfoDto {

    private Long id;
    private String name;
    private String email;
    private String picture;
    private Role role;

    public MemberInfoDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
        this.role = member.getRole();
    }
}
