package com.kwon770.mm.web;

import com.kwon770.mm.domain.post.Post;
import com.kwon770.mm.domain.post.PostImage;
import com.kwon770.mm.exception.NotAuthorException;
import com.kwon770.mm.service.post.PostService;
import com.kwon770.mm.web.dto.post.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<Long> createPost(@RequestBody PostRequestDto postRequestDto, @RequestParam(value = "image", required = false, defaultValue = "") List<MultipartFile> images) {
        Long postId = postService.createPost(postRequestDto, images);

        return new ResponseEntity<>(postId, HttpStatus.OK);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<Long> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, @RequestParam(value = "image", required = false, defaultValue = "") List<MultipartFile> images) {
        postService.validateAuthor(postId);

        postService.updatePost(postId, postRequestDto, images);

        return new ResponseEntity<>(postId, HttpStatus.OK);
    }
}
