package com.kwon770.mm.service.post;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.post.Post;
import com.kwon770.mm.domain.post.comment.Comment;
import com.kwon770.mm.domain.post.comment.CommentRepository;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.service.member.MemberService;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.dto.post.CommentInfoDto;
import com.kwon770.mm.dto.post.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final PostService postService;

    public Long createComment(Long postId, CommentRequestDto commentRequestDto) {
        Member author = memberService.getMeById();
        Post post = postService.getPostByPostId(postId);
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
        List<Comment> comments = postService.getPostByPostId(postId).getComments();

        return comments.stream().map(CommentInfoDto::new).collect(Collectors.toList());
    }

    @Transactional
    public boolean toggleCommentLike(Long commentId) {
        Member member = memberService.getMeById();
        Comment comment = findById(commentId);
        if (comment.getDidLike(member.getId())) {
            member.subtractedLikedComment(comment);
            return false;
        } else {
            member.appendLikedComment(comment);
            return true;
        }
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public void validateAuthor(Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Comment comment = findById(commentId);
        if (!comment.getAuthor().getId().equals(currentMemberId)) {
            throw new IllegalArgumentException(ErrorCode.NOT_AUTHOR_MESSAGE + commentId);
        }
    }
}
