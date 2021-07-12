package com.kwon770.mm.web.dto;

import com.kwon770.mm.domain.user.Title;
import com.kwon770.mm.domain.user.UserTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserTitleSaveDto {

    private Title title;

    @Builder
    public UserTitleSaveDto(Title title) { this.title = title; }

    public UserTitle toEntity() {
        return UserTitle.builder()
                .title(title)
                .build();
    }
}
