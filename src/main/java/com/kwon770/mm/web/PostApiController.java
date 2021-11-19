package com.kwon770.mm.web;

import com.kwon770.mm.service.post.PostService;
import com.kwon770.mm.web.dto.post.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/post")
    public Long createPost(@RequestBody PostRequestDto postRequestDto, @RequestParam(value = "image", required = false, defaultValue = "") List<MultipartFile> images) {
        return postService.createPost(postRequestDto, images);
    }
}
