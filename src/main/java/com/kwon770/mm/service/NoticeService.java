package com.kwon770.mm.service;

import com.kwon770.mm.domain.notice.NoticeRepository;
import com.kwon770.mm.web.dto.NoticeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public Long save(NoticeDto notice) {
        return noticeRepository.save(notice.toEntity()).getId();
    }

    public NoticeDto getLatestNoticeDto() {
        return new NoticeDto(noticeRepository.findTopByOrderByIdDesc());
    }
}
