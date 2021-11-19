package com.kwon770.mm.service.post;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.post.Post;
import com.kwon770.mm.domain.post.comment.Comment;
import com.kwon770.mm.domain.post.comment.CommentRepository;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.service.member.MemberService;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.web.dto.post.CommentInfoDto;
import com.kwon770.mm.web.dto.post.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final PostService postService;

    public Long createComment(Long postId, CommentRequestDto commentRequestDto) {
        Member author = memberService.getMemberById(SecurityUtil.getCurrentMemberId());
        Post post = postService.findById(postId);
        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .post(post)
                .author(author)
                .build();

        commentRepository.save(comment);
        return comment.getId();
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_COMMENT_BY_COMMENTID + commentId));
    }

    public List<CommentInfoDto> getCommentInfoDtosByPostId(Long postId) {
        List<Comment> comments = postService.findById(postId).getComments();

        return comments.stream().map(CommentInfoDto::new).collect(Collectors.toList());
    }

    public boolean toggleCommentLike(Long commentId) {
        Member me = memberService.getMemberById(SecurityUtil.getCurrentMemberId());
        Comment comment = findById(commentId);
        if (comment.getDidLike(me.getId())) {
            me.subtractedLikedComment(comment);
            return false;
        } else {
            me.appendLikedComment(comment);
            return true;
        }
    }

    public void validateAuthor(Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Comment comment = findById(commentId);
        if (!comment.getAuthor().getId().equals(currentMemberId)) {
            throw new IllegalArgumentException(ErrorCode.NOT_AUTHOR_MESSAGE + commentId);
        }
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
