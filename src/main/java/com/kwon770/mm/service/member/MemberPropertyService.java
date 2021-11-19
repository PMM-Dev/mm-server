package com.kwon770.mm.service.member;

import com.kwon770.mm.domain.report.Report;
import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.member.Title;
import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.member.MemberTitle;
import com.kwon770.mm.domain.member.MemberTitleRepository;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.service.ReportService;
import com.kwon770.mm.service.restaurant.RestaurantMapper;
import com.kwon770.mm.service.restaurant.RestaurantService;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.web.dto.MemberTitleRequestDto;
import com.kwon770.mm.web.dto.Restaurant.RestaurantElementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberPropertyService {

    private final MemberService memberService;
    private final MemberTitleRepository memberTitleRepository;

    private final RestaurantService restaurantService;
    private final ReportService reportService;

    public Long saveTitle(MemberTitleRequestDto memberTitleRequestDto) {
        return memberTitleRepository.save(memberTitleRequestDto.toEntity()).getId();
    }

    public void deleteTitle(String title) {
        Optional<MemberTitle> memberTitle = memberTitleRepository.findByTitle(Title.valueOf(title));
        if (memberTitle.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_TITLE_MESSAGE + title);
        }

        memberTitleRepository.delete(memberTitle.get());
    }

    @Transactional
    public void appendTitle(String title) {
        Member member = memberService.getMemberById(SecurityUtil.getCurrentMemberId());
        Optional<MemberTitle> memberTitle = memberTitleRepository.findByTitle(Title.valueOf(title));
        if (memberTitle.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_TITLE_MESSAGE + title);
        }

        member.appendTitle(memberTitle.get());
    }

    @Transactional
    public void subtractTitle(String title) {
        Member member = memberService.getMemberById(SecurityUtil.getCurrentMemberId());
        Optional<MemberTitle> memberTitle = memberTitleRepository.findByTitle(Title.valueOf(title));
        if (memberTitle.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NO_TITLE_MESSAGE + title);
        }

        member.subtractTitle(memberTitle.get());
    }

    public List<RestaurantElementDto> getLikedRestaurantList(Long userId) {
        Member member = memberService.getMemberById(userId);
        List<Restaurant> likedRestaurantEntities = member.getLikedRestaurants();

        return RestaurantMapper.INSTANCE.restaurantsToRestaurantElementDtos(likedRestaurantEntities);
    }

    @Transactional
    public void appendLikedRestaurant(Long restaurantId) {
        Member member = memberService.getMemberById(SecurityUtil.getCurrentMemberId());
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        member.appendLikedRestaurant(restaurant);
    }

    @Transactional
    public void subtractedLikedRestaurant(Long restaurantId) {
        Member member = memberService.getMemberById(SecurityUtil.getCurrentMemberId());
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        member.subtractedLikedRestaurant(restaurant);
    }

    @Transactional
    public void appendLikedReport(Long reportId) {
        Member member = memberService.getMemberById(SecurityUtil.getCurrentMemberId());
        Report report = reportService.getReportById(reportId);

        member.appendLikedReport(report);
    }

    @Transactional
    public void subtractedLikedReport(Long reportId) {
        Member member = memberService.getMemberById(SecurityUtil.getCurrentMemberId());
        Report report = reportService.getReportById(reportId);

        member.subtractedLikedReport(report);
    }
}
