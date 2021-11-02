package com.kwon770.mm.service;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.member.MemberRepository;
import com.kwon770.mm.domain.report.Report;
import com.kwon770.mm.domain.report.ReportQueryRepository;
import com.kwon770.mm.domain.report.ReportRepository;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.web.dto.ReportInfoDto;
import com.kwon770.mm.web.dto.ReportPreviewDto;
import com.kwon770.mm.web.dto.ReportRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        Member me = memberService.getMemberById(SecurityUtil.getCurrentMemberId());

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
            throw new IllegalArgumentException("해당 id와 일치하는 Report가 없습니다 reportId="+reportId);
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

    public void deleteMyReportByReportId(Long reportId) {
        Optional<Report> report = reportRepository.findById(reportId);
        if (report.isEmpty()) {
            throw new IllegalArgumentException("해당 id와 일치하는 Report가 없습니다 reportId="+reportId);
        }
        if (!report.get().getAuthor().getId().equals(SecurityUtil.getCurrentMemberId())) {
            throw new IllegalArgumentException("해당 Report의 소유자가 아닙니다");
        }

        report.get().removeAllMemberConnection();
        reportRepository.deleteById(reportId);
    }
}
