package com.kwon770.mm.service;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.post.Post;
import com.kwon770.mm.domain.post.PostImage;
import com.kwon770.mm.domain.report.Report;
import com.kwon770.mm.domain.report.ReportRepository;
import com.kwon770.mm.domain.report.ReportType;
import com.kwon770.mm.domain.restaurant.review.RestaurantReview;
import com.kwon770.mm.service.member.MemberService;
import com.kwon770.mm.service.post.PostService;
import com.kwon770.mm.service.restaurant.review.RestaurantReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final PostService postService;
    private final RestaurantReviewService restaurantReviewService;
    private final MemberService memberService;

    public Long reportPost(Long postId) {
        Post post = postService.getPostByPostId(postId);
        Member postAuthor = memberService.getMemberById(post.getAuthor().getId());
        Member me = memberService.getMeById();
        StringBuilder content = new StringBuilder();
        content.append(post.getTitle() + "\n");
        content.append(post.getContent() + "\n");
        for (PostImage image : post.getPostImages()) {
            content.append(image.getFilePath() + "\n");
        }

        Report report = Report.builder()
                .reportType(ReportType.POST)
                .reportTargetId(post.getId())
                .reporter(me)
                .reported(postAuthor)
                .content(content.toString())
                .build();
        reportRepository.save(report);
        return report.getId();
    }

    public Long reportReview(Long reviewId) {
        RestaurantReview restaurantReview = restaurantReviewService.getReviewByReviewId(reviewId);
        Member reviewAuthor = memberService.getMemberById(restaurantReview.getAuthor().getId());
        Member me = memberService.getMeById();
        StringBuilder content = new StringBuilder();
        content.append(restaurantReview.getDescription() + "\n");
        if (restaurantReview.getRestaurantReviewImage() != null) {
            content.append(restaurantReview.getRestaurantReviewImage().getFilePath() + "\n");
        }

        Report report = Report.builder()
                .reportType(ReportType.REVIEW)
                .reportTargetId(restaurantReview.getId())
                .reporter(me)
                .reported(reviewAuthor)
                .content(content.toString())
                .build();
        reportRepository.save(report);
        return report.getId();
    }
}
