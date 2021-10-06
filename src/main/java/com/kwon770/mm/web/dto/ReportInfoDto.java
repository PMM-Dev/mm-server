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
public class ReportInfoDto {

    private Long id;
    private String authorName;
    private String authorPicture;
    private String content;
    private Integer likeCount;

    public ReportInfoDto(Report report) {
        this.id = report.getId();
        this.authorName = report.getAuthor().getName();
        this.authorPicture = report.getAuthor().getPicture();
        this.content = report.getContent();
        this.likeCount = report.getLikeCount();
    }
}
