package com.kwon770.mm.web.dto.Restaurant;

import com.kwon770.mm.domain.review.Review;
import com.kwon770.mm.util.CommonUtil;
import lombok.*;

import java.text.SimpleDateFormat;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class ReviewInfoDto {

    private Long id;
    private String createdDate;
    private String authorName;
    private String description;
    private Float grade;

    public ReviewInfoDto(Review review) {
        this.id = review.getId();
        this.createdDate = CommonUtil.convertLocalDateTimeToFormatString(review.getCreatedDate());
        this.authorName = review.getAuthor().getName();
        this.description = review.getDescription();
        this.grade = review.getGrade();
    }
}
