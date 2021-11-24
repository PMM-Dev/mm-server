package com.kwon770.mm.domain.report;

import com.kwon770.mm.domain.BaseTimeEntity;
import com.kwon770.mm.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Entity
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @NotNull
    private Long reportTargetId;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private Member reporter;

    @NotNull
    private String reporterMemberEmail;

    @ManyToOne
    @JoinColumn(name = "reported_id")
    private Member reported;

    @NotNull
    private String reportedMemberEmail;

    @NotNull
    private String content;

    @Builder
    public Report(ReportType reportType, Long reportTargetId, Member reporter, Member reported, String content) {
        this.reportType = reportType;
        this.reportTargetId = reportTargetId;
        this.reporter = reporter;
        this.reporterMemberEmail = reporter.getEmail();
        this.reported = reported;
        this.reportedMemberEmail = reported.getEmail();
        this.content = content;
    }
}
