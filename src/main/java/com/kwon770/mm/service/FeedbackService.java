package com.kwon770.mm.service;

import com.kwon770.mm.domain.feedback.Feedback;
import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.feedback.FeedbackQueryRepository;
import com.kwon770.mm.domain.feedback.FeedbackRepository;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.service.member.MemberService;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.dto.feedback.FeedbackInfoDto;
import com.kwon770.mm.dto.feedback.FeedbackPreviewDto;
import com.kwon770.mm.dto.feedback.FeedbackRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FeedbackService {

    private final MemberService memberService;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackQueryRepository feedbackQueryRepository;

    public Long save(FeedbackRequestDto feedbackRequestDto) {
        Member me = memberService.getMeById();

        return feedbackRepository.save(feedbackRequestDto.toEntity(me)).getId();
    }

    public Optional<FeedbackPreviewDto> getLatestFeedbackPreviewDto() {
        Optional<Feedback> feedback = feedbackRepository.findTopByOrderByCreatedDateDesc();
        if (feedback.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new FeedbackPreviewDto(feedback.get()));
    }

    public Feedback getFeedbackById(Long feedbackId) {
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        if (feedback.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_REPORT_BY_REPORTID + feedbackId);
        }

        return feedback.get();
    }

    public List<FeedbackInfoDto> getFeedbacksOrderByCreatedDateDesc() {
        List<Feedback> feedbacks = feedbackRepository.findAllByOrderByCreatedDateDesc();

        return feedbacks.stream().map(feedback -> new FeedbackInfoDto(feedback)).collect(Collectors.toList());
    }

    public List<FeedbackInfoDto> getFeedbacksOrderByLikeCountDesc() {
        List<Feedback> feedbacks = feedbackQueryRepository.findAllOrderByLikeCountDesc();

        return feedbacks.stream().map(feedback -> new FeedbackInfoDto(feedback)).collect(Collectors.toList());
    }

    @Transactional
    public void likeFeedback(Long feedbackId) {
        Member member = memberService.getMeById();
        Feedback feedback = getFeedbackById(feedbackId);
        member.appendLikedFeedback(feedback);
    }

    @Transactional
    public void unlikeFeedback(Long feedbackId) {
        Member member = memberService.getMeById();
        Feedback feedback = getFeedbackById(feedbackId);
        member.subtractedLikedFeedback(feedback);
    }

    @Transactional
    public void deleteMyFeedbackByFeedbackId(Long feedbackId) {
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        if (feedback.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_REPORT_BY_REPORTID + feedbackId);
        }
        if (!feedback.get().getAuthor().getId().equals(SecurityUtil.getCurrentMemberId())) {
            throw new IllegalArgumentException(ErrorCode.NOT_REPORT_OWNER);
        }

        feedback.get().removeAllMemberConnection();
        feedbackRepository.deleteById(feedbackId);
    }
}
