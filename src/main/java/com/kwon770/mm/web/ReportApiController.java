package com.kwon770.mm.web;

import com.kwon770.mm.service.ReportService;
import com.kwon770.mm.web.dto.ReportInfoDto;
import com.kwon770.mm.web.dto.ReportPreviewDto;
import com.kwon770.mm.web.dto.ReportRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReportApiController {

    private final ReportService reportService;

    @PostMapping("/report")
    public Long uploadReport(@RequestBody ReportRequestDto reportRequestDto) {
        return reportService.save(reportRequestDto);
    }

    @GetMapping("/report/preview")
    public ReportPreviewDto getLatestReportPreviewDto() {
        return reportService.getLatestReportPreviewDto();
    }

    @GetMapping("/report/orderBy/createdDateDesc")
    public List<ReportInfoDto> getReportsOrderByCreatedDateDesc() {
        return reportService.getReportsOrderByCreatedDateDesc();
    }

    @GetMapping("/report/orderBy/likeCountDesc")
    public List<ReportInfoDto> getReportsOrderByLikeCountDesc() {
        return reportService.getReportsOrderByLikeCountDesc();
    }

    @DeleteMapping("/report/{reportId}")
    public boolean deleteMyReportByReportId(@PathVariable Long reportId) {
        reportService.deleteMyReportByReportId(reportId);
        return true;
    }
}
