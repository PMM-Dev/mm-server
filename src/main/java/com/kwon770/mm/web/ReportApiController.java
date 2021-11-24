package com.kwon770.mm.web;

import com.kwon770.mm.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReportApiController {

    private final ReportService reportService;

    @PostMapping("/post/{postId}/report")
    public ResponseEntity<Long> reportPost(@PathVariable Long postId) {
        Long reportId = reportService.reportPost(postId);

        return new ResponseEntity<>(reportId, HttpStatus.OK);
    }

    @PostMapping("restaurant/review/{reviewId}/report")
    public ResponseEntity<Long> reportReview(@PathVariable Long reviewId) {
        Long reportId = reportService.reportReview(reviewId);

        return new ResponseEntity<>(reportId, HttpStatus.OK);
    }
}
