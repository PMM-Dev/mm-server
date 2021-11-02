package com.kwon770.mm.web;

import com.kwon770.mm.service.ReportService;
import com.kwon770.mm.web.dto.ReportInfoDto;
import com.kwon770.mm.web.dto.ReportPreviewDto;
import com.kwon770.mm.web.dto.ReportRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ReportApiController {

    private final ReportService reportService;

    @PostMapping("/report")
    public ResponseEntity<Long> uploadReport(@RequestBody ReportRequestDto reportRequestDto) {
        Long reportId = reportService.save(reportRequestDto);

        return new ResponseEntity<>(reportId, HttpStatus.OK);
    }

    @GetMapping("/report/preview")
    public ResponseEntity<ReportPreviewDto> getLatestReportPreviewDto() {
        Optional<ReportPreviewDto> reportPreviewDto = reportService.getLatestReportPreviewDto();
        if (reportPreviewDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(reportPreviewDto.get(), HttpStatus.OK);
    }

    @GetMapping("/report/orderBy/createdDateDesc")
    public ResponseEntity<List<ReportInfoDto>> getReportsOrderByCreatedDateDesc() {
        List<ReportInfoDto> reportInfoDtos = reportService.getReportsOrderByCreatedDateDesc();

        return new ResponseEntity<>(reportInfoDtos, HttpStatus.OK);
    }

    @GetMapping("/report/orderBy/likeCountDesc")
    public ResponseEntity<List<ReportInfoDto>> getReportsOrderByLikeCountDesc() {
        List<ReportInfoDto> reportInfoDtos = reportService.getReportsOrderByLikeCountDesc();

        return new ResponseEntity<>(reportInfoDtos, HttpStatus.OK);
    }

    @DeleteMapping("/report/{reportId}")
    public ResponseEntity<Void> deleteMyReportByReportId(@PathVariable Long reportId) {
        reportService.deleteMyReportByReportId(reportId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
