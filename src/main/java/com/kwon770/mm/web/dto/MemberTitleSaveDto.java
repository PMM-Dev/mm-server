package com.kwon770.mm.web.dto;

import com.kwon770.mm.domain.member.Title;
import com.kwon770.mm.domain.member.MemberTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberTitleSaveDto {

    private Title title;

    @Builder
    public MemberTitleSaveDto(Title title) { this.title = title; }

    public MemberTitle toEntity() {
        return MemberTitle.builder()
                .title(title)
                .build();
    }
}
