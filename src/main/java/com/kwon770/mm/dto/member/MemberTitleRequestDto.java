package com.kwon770.mm.dto.member;

import com.kwon770.mm.domain.member.Title;
import com.kwon770.mm.domain.member.MemberTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberTitleRequestDto {

    private Title title;

    @Builder
    public MemberTitleRequestDto(Title title) { this.title = title; }

    public MemberTitle toEntity() {
        return MemberTitle.builder()
                .title(title)
                .build();
    }
}
