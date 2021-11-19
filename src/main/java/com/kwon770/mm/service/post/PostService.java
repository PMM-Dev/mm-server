package com.kwon770.mm.service.post;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.post.Post;
import com.kwon770.mm.domain.post.PostImage;
import com.kwon770.mm.domain.post.PostImageRepository;
import com.kwon770.mm.domain.post.PostRepository;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.exception.NotAuthorException;
import com.kwon770.mm.service.ImageHandler;
import com.kwon770.mm.service.member.MemberService;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.web.dto.post.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final ImageHandler imageHandler;
    private final PostImageRepository postImageRepository;
    private final MemberService memberService;

    public Long createPost(PostRequestDto postRequestDto, List<MultipartFile> images) {
        Member author = memberService.getMemberById(SecurityUtil.getCurrentMemberId());
        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .author(author)
                .build();
        List<PostImage> postImages = getPostImages(post, images);

        postRepository.save(post);
        postImageRepository.saveAll(postImages);
        return post.getId();
    }

    private List<PostImage> getPostImages(Post post, List<MultipartFile> images) {
        return images.stream().map(image -> {
            PostImage postImage = imageHandler.parsePostImage(post, image);
            imageHandler.downloadImage(image, postImage.getFilePath());
            return postImage;
        }).collect(Collectors.toList());
    }

    public void validateAuthor(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Post post = findById(postId);
        if (!currentMemberId.equals(post.getAuthor().getId())) {
            throw new NotAuthorException(currentMemberId);
        }
    }

    public void updatePost(Long postId, PostRequestDto postRequestDto, List<MultipartFile> images) {
        Post post = findById(postId);

        List<PostImage> removedPostImages = post.getRemovedPostImages(images);
        postImageRepository.deleteAll(removedPostImages);

        List<MultipartFile> addedImages = post.getAddedPostImages(images);
        List<PostImage> addedPostImage = getPostImages(post, addedImages);

        post.update(postRequestDto, addedPostImage);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_POST_BY_POSTID + postId));
    }
}
