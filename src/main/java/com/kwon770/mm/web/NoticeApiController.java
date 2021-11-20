package com.kwon770.mm.web;

import com.kwon770.mm.service.NoticeService;
import com.kwon770.mm.dto.NoticeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class NoticeApiController {

    private final NoticeService noticeService;

    @PostMapping("/notice")
    public ResponseEntity<Long> uploadNotice(@RequestBody NoticeDto noticeDto) {
        Long noticeId = noticeService.save(noticeDto);

        return new ResponseEntity<>(noticeId, HttpStatus.OK);
    }

    @GetMapping("/notice")
    public ResponseEntity<NoticeDto> getLatestNoticeDto() {
        Optional<NoticeDto> noticeDto = noticeService.getLatestNoticeDto();
        if (noticeDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
