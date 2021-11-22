package com.kwon770.mm.web.post;

import com.kwon770.mm.service.post.PostService;
import com.kwon770.mm.dto.post.PostInfoDto;
import com.kwon770.mm.dto.post.PostElementDto;
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
    public ResponseEntity<Long> createPost(@RequestParam(value = "title") String title,
                                           @RequestParam(value = "content") String content,
                                           @RequestParam(value = "images", required = false, defaultValue = "") List<MultipartFile> images) {

        Long postId = postService.createPost(title, content, images);

        return new ResponseEntity<>(postId, HttpStatus.OK);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<Long> updatePost(@PathVariable Long postId,
                                           @RequestParam(value = "title") String title,
                                           @RequestParam(value = "content") String content,
                                           @RequestParam(value = "images", required = false, defaultValue = "") List<MultipartFile> images) {
        postService.validateAuthor(postId);

        postService.updatePost(postId, title, content, images);

        return new ResponseEntity<>(postId, HttpStatus.OK);
    }

    @GetMapping("/post")
    public ResponseEntity<List<PostElementDto>> getPostElementDtoList() {
        List<PostElementDto> postElementDtos = postService.getPostElementDtos();

        return new ResponseEntity<>(postElementDtos, HttpStatus.OK);
    }

    @GetMapping("/post/preview")
    public ResponseEntity<List<PostElementDto>> getLatest3PostElementDtoList() {
        List<PostElementDto> latest3PostElementDtos = postService.getLatest3PostElementDtos();

        return new ResponseEntity<>(latest3PostElementDtos, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostInfoDto> getPostInfoDtoByPostId(@PathVariable Long postId) {
        PostInfoDto postInfoDto = postService.getPostInfoDto(postId);

        return new ResponseEntity<>(postInfoDto, HttpStatus.OK);
    }

    @PutMapping("/post/{postId}/like")
    public ResponseEntity<Boolean> togglePostLike(@PathVariable Long postId) {
        boolean didLike = postService.togglePostLike(postId);

        return new ResponseEntity<>(didLike, HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deletePostByPostId(@PathVariable Long postId) {
        postService.validateAuthor(postId);

        postService.deletePostByPostId(postId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
