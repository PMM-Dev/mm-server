package com.kwon770.mm.web;

import com.kwon770.mm.service.post.CommentService;
import com.kwon770.mm.web.dto.post.CommentInfoDto;
import com.kwon770.mm.web.dto.post.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<Long> createCommentOnPostId(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto) {
        Long commentId = commentService.createComment(postId, commentRequestDto);

        return new ResponseEntity<>(commentId, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}/comment")
    public ResponseEntity<List<CommentInfoDto>> getCommentInfoDtoListByPostId(@PathVariable Long postId) {
        List<CommentInfoDto> commentInfoDtos = commentService.getCommentInfoDtosByPostId(postId);

        return new ResponseEntity<>(commentInfoDtos, HttpStatus.OK);
    }

    @PutMapping("/post/comment/{commentId}")
    public ResponseEntity<Boolean> toggleCommentLike(@PathVariable Long commentId) {
        boolean didLike = commentService.toggleCommentLike(commentId);

        return new ResponseEntity<>(didLike, HttpStatus.OK);
    }

    @DeleteMapping("/post/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.validateAuthor(commentId);

        commentService.deleteComment(commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
