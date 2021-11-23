package com.kwon770.mm.web;

import com.kwon770.mm.service.FeedbackService;
import com.kwon770.mm.dto.feedback.FeedbackInfoDto;
import com.kwon770.mm.dto.feedback.FeedbackPreviewDto;
import com.kwon770.mm.dto.feedback.FeedbackRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class FeedbackApiController {

    private final FeedbackService feedbackService;

    @PostMapping("/feedback")
    public ResponseEntity<Long> uploadFeedback(@RequestBody FeedbackRequestDto feedbackRequestDto) {
        Long feedbackId = feedbackService.save(feedbackRequestDto);

        return new ResponseEntity<>(feedbackId, HttpStatus.OK);
    }

    @GetMapping("/feedback/preview")
    public ResponseEntity<FeedbackPreviewDto> getLatestFeedbackPreviewDto() {
        Optional<FeedbackPreviewDto> feedbackPreviewDto = feedbackService.getLatestFeedbackPreviewDto();
        if (feedbackPreviewDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(feedbackPreviewDto.get(), HttpStatus.OK);
    }

    @GetMapping("/feedback/orderBy/createdDateDesc")
    public ResponseEntity<List<FeedbackInfoDto>> getFeedbacksOrderByCreatedDateDesc() {
        List<FeedbackInfoDto> feedbackInfoDtos = feedbackService.getFeedbacksOrderByCreatedDateDesc();

        return new ResponseEntity<>(feedbackInfoDtos, HttpStatus.OK);
    }

    @GetMapping("/feedback/orderBy/likeCountDesc")
    public ResponseEntity<List<FeedbackInfoDto>> getFeedbacksOrderByLikeCountDesc() {
        List<FeedbackInfoDto> feedbackInfoDtos = feedbackService.getFeedbacksOrderByLikeCountDesc();

        return new ResponseEntity<>(feedbackInfoDtos, HttpStatus.OK);
    }

    @PutMapping("/member/like/feedback/{feedbackId}")
    public ResponseEntity<Void> likeFeedback(@PathVariable Long feedbackId) {
        feedbackService.likeFeedback(feedbackId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/member/like/feedback/{feedbackId}")
    public ResponseEntity<Void> unlikeFeedback(@PathVariable Long feedbackId) {
        feedbackService.unlikeFeedback(feedbackId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/feedback/{feedbackId}")
    public ResponseEntity<Void> deleteMyFeedbackByFeedbackId(@PathVariable Long feedbackId) {
        feedbackService.deleteMyFeedbackByFeedbackId(feedbackId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
