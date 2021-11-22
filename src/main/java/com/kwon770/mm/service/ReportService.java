package com.kwon770.mm.service;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.report.Report;
import com.kwon770.mm.domain.report.ReportQueryRepository;
import com.kwon770.mm.domain.report.ReportRepository;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.service.member.MemberService;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.dto.report.ReportInfoDto;
import com.kwon770.mm.dto.report.ReportPreviewDto;
import com.kwon770.mm.dto.report.ReportRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final MemberService memberService;
    private final ReportRepository reportRepository;
    private final ReportQueryRepository reportQueryRepository;

    public Long save(ReportRequestDto reportRequestDto) {
        Member me = memberService.getMeById();

        return reportRepository.save(reportRequestDto.toEntity(me)).getId();
    }

    public Optional<ReportPreviewDto> getLatestReportPreviewDto() {
        Optional<Report> report = reportRepository.findTopByOrderByCreatedDateDesc();
        if (report.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new ReportPreviewDto(report.get()));
    }

    public Report getReportById(Long reportId) {
        Optional<Report> report = reportRepository.findById(reportId);
        if (report.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_REPORT_BY_REPORTID + reportId);
        }

        return report.get();
    }

    public List<ReportInfoDto> getReportsOrderByCreatedDateDesc() {
        List<Report> reports = reportRepository.findAllByOrderByCreatedDateDesc();

        return reports.stream().map(report -> new ReportInfoDto(report)).collect(Collectors.toList());
    }

    public List<ReportInfoDto> getReportsOrderByLikeCountDesc() {
        List<Report> reports = reportQueryRepository.findAllOrderByLikeCountDesc();

        return reports.stream().map(report -> new ReportInfoDto(report)).collect(Collectors.toList());
    }

    @Transactional
    public void likeReport(Long reportId) {
        Member member = memberService.getMeById();
        Report report = getReportById(reportId);
        member.appendLikedReport(report);
    }

    @Transactional
    public void unlikeReport(Long reportId) {
        Member member = memberService.getMeById();
        Report report = getReportById(reportId);
        member.subtractedLikedReport(report);
    }

    public void deleteMyReportByReportId(Long reportId) {
        Optional<Report> report = reportRepository.findById(reportId);
        if (report.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_REPORT_BY_REPORTID + reportId);
        }
        if (!report.get().getAuthor().getId().equals(SecurityUtil.getCurrentMemberId())) {
            throw new IllegalArgumentException(ErrorCode.NOT_REPORT_OWNER);
        }

        report.get().removeAllMemberConnection();
        reportRepository.deleteById(reportId);
    }
}
