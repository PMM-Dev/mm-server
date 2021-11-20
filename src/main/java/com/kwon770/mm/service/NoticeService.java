package com.kwon770.mm.service;

import com.kwon770.mm.domain.notice.Notice;
import com.kwon770.mm.domain.notice.NoticeRepository;
import com.kwon770.mm.dto.NoticeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public Long save(NoticeDto notice) {
        return noticeRepository.save(notice.toEntity()).getId();
    }

    public Optional<NoticeDto> getLatestNoticeDto() {
        Optional<Notice> notice = noticeRepository.findTopByOrderByIdDesc();
        if (notice.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new NoticeDto(notice.get()));
    }
}
