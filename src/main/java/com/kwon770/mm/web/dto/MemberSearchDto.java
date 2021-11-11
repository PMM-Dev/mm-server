package com.kwon770.mm.web.dto;

import com.kwon770.mm.domain.member.Member;
import lombok.Getter;

@Getter
public class MemberSearchDto {

    private Long id;
    private String name;
    private String email;
    private String picture;

    public MemberSearchDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}
