package com.kwon770.mm.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberUpdateDto {

    private String name;
    private String picture;
}
