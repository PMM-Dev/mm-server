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
import com.kwon770.mm.dto.post.PostInfoDto;
import com.kwon770.mm.dto.post.PostElementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final MemberService memberService;

    private final ImageHandler imageHandler;

    @Transactional
    public Long createPost(String title, String content, List<MultipartFile> images) {
        Member author = memberService.getMeById();
        Post post = Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
        List<PostImage> postImages = generatePostImages(post, images);

        postRepository.save(post);
        postImageRepository.saveAll(postImages);
        return post.getId();
    }

    private List<PostImage> generatePostImages(Post post, List<MultipartFile> images) {
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

    @Transactional
    public void updatePost(Long postId, String title, String content, List<MultipartFile> images) {
        Post post = findById(postId);

        List<PostImage> removedPostImages = post.getRemovedPostImages(images);
        postImageRepository.deleteAll(removedPostImages);

        List<MultipartFile> addedImages = post.getAddedPostImages(images);
        List<PostImage> addedPostImages = generatePostImages(post, addedImages);

        post.update(title, content, addedPostImages);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_POST_BY_POSTID + postId));
    }

    public List<PostElementDto> getPostElementDtos() {
        List<Post> posts = postRepository.findAll();

        return posts.stream().map(PostElementDto::new).collect(Collectors.toList());
    }

    public List<PostElementDto> getLatest3PostElementDtos() {
        List<Post> posts = postRepository.findLimit3ByOrderByCreatedDateDesc();

        return posts.stream().map(PostElementDto::new).collect(Collectors.toList());
    }

    public PostInfoDto getPostInfoDto(Long postId) {
        Post post = findById(postId);
        post.increaseViewCount();

        return new PostInfoDto(post);
    }

    public Optional<String> getPostImagePathOnIndexByPostId(Long postId, int index) {
        Post post = findById(postId);
        try {
            return Optional.of(post.getPostImages().get(index).getFilePath());
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(ErrorCode.NOT_FOUND_IMAGE_BY_INDEX + index);
        }
    }

    @Transactional
    public void likePost(Long postId) {
        Member member = memberService.getMeById();
        Post post = findById(postId);
        member.appendLikedPost(post);
    }

    @Transactional
    public void unlikePost(Long postId) {
        Member member = memberService.getMeById();
        Post post = findById(postId);
        member.subtractedLikedPost(post);
    }

    public void deletePostByPostId(Long postId) {
        postRepository.deleteById(postId);
    }
}
