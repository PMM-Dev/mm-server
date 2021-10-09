package com.kwon770.mm.web.dto;

import com.kwon770.mm.domain.report.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class ReportPreviewDto {

    private Long id;
    private String authorName;
    private String content;
    private Integer likeCount;

    public ReportPreviewDto(Report report) {
        this.id = report.getId();
        this.authorName = report.getAuthor().getName();
        this.content = report.getContent();
        this.likeCount = report.getLikingMembers().size();
    }
}
