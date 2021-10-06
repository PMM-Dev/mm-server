package com.kwon770.mm.web.dto;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.report.Report;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportRequestDto {

    private String content;

    @Builder
    public ReportRequestDto(String content) {
        this.content = content;
    }

    public Report toEntity(Member author) {
        return Report.builder()
                .author(author)
                .content(content)
                .build();
    }
}
