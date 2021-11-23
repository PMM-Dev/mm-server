package com.kwon770.mm.dto.post;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class PostRequestDto {

    private String title;
    private String content;
    private List<MultipartFile> images;

    @Builder
    public PostRequestDto(String title, String content, List<MultipartFile> images) {
        this.title = title;
        this.content = content;
        this.images = images;
    }
}
