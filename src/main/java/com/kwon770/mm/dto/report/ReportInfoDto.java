package com.kwon770.mm.dto.report;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.report.Report;
import com.kwon770.mm.util.CommonUtil;
import com.kwon770.mm.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class ReportInfoDto {

    private Long id;
    private String createdDate;
    private String authorEmail;
    private String authorPicture;
    private String content;
    private Integer likeCount;

    private boolean didLike;

    public ReportInfoDto(Report report) {
        this.id = report.getId();
        this.createdDate = CommonUtil.convertLocalDateTimeToFormatString(report.getCreatedDate());
        this.authorEmail = report.getAuthor().getEmail();
        this.authorPicture = report.getAuthor().getPicture();
        this.content = report.getContent();
        this.likeCount = report.getLikingMembers().size();

        calculateDidLike(report.getLikingMembers());
    }

    private void calculateDidLike(List<Member> LikingMembers) {
        Long userId = SecurityUtil.getCurrentMemberId();
        for (Member likedMember : LikingMembers) {
            if (userId.equals(likedMember.getId())) {
                this.didLike = true;
                break;
            }
        }
    }
}
