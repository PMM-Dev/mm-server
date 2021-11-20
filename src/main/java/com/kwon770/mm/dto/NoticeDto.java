package com.kwon770.mm.dto;

import com.kwon770.mm.domain.notice.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class NoticeDto {

    private Long id;
    private String content;

    public NoticeDto(Notice notice) {
        this.id = notice.getId();
        this.content = notice.getContent();
    }

    public Notice toEntity() {
        return Notice.builder()
                .content(content)
                .build();
    }
}
