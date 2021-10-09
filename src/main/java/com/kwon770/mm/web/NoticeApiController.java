package com.kwon770.mm.web;

import com.kwon770.mm.service.NoticeService;
import com.kwon770.mm.web.dto.NoticeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NoticeApiController {

    private final NoticeService noticeService;

    @PostMapping("/notice")
    public Long uploadNotice(@RequestBody NoticeDto noticeDto) {
        return noticeService.save(noticeDto);
    }

    @GetMapping("/notice")
    public NoticeDto getLatestNoticeDto() {
        return noticeService.getLatestNoticeDto();
    }
}
