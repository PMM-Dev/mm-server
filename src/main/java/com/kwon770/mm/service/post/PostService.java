package com.kwon770.mm.service.post;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.post.Post;
import com.kwon770.mm.domain.post.PostImage;
import com.kwon770.mm.domain.post.PostImageRepository;
import com.kwon770.mm.domain.post.PostRepository;
import com.kwon770.mm.domain.restaurant.RestaurantImage;
import com.kwon770.mm.dto.post.PostRequestDto;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.exception.NotAuthorException;
import com.kwon770.mm.service.ImageHandler;
import com.kwon770.mm.service.member.MemberService;
import com.kwon770.mm.util.CommonUtil;
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
    public Long createPost(PostRequestDto postRequestDto) {
        Member author = memberService.getMeById();
        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .author(author)
                .build();
        List<PostImage> postImages = generatePostImages(post, postRequestDto.getImages());

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

    @Transactional
    public void updatePost(Long postId, PostRequestDto postRequestDto) {
        Post post = getPostByPostId(postId);
        post.update(postRequestDto.getTitle(), postRequestDto.getContent());

        List<PostImage> oldPostImages = post.getPostImages();
        for (PostImage oldImage : oldPostImages) {
            CommonUtil.removeImageFromServer(oldImage.getFilePath());
            postImageRepository.delete(oldImage);
        }

        List<PostImage> newPostImages = generatePostImages(post, postRequestDto.getImages());
        postImageRepository.saveAll(newPostImages);
    }

    public Post getPostByPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_POST_BY_POSTID + postId));
    }

    public List<PostElementDto> getPostElementDtos() {
        List<Post> posts = postRepository.findAll();

        return posts.stream().map(PostElementDto::new).collect(Collectors.toList());
    }

    public List<PostElementDto> getLatest3PostElementDtos() {
        List<Post> posts = postRepository.findTop3ByOrderByCreatedDateDesc();

        return posts.stream().map(PostElementDto::new).collect(Collectors.toList());
    }

    public PostInfoDto getPostInfoDto(Long postId) {
        Post post = getPostByPostId(postId);
        post.increaseViewCount();

        return new PostInfoDto(post);
    }

    public Optional<String> getPostImagePathOnIndexByPostId(Long postId, int index) {
        Post post = getPostByPostId(postId);
        try {
            return Optional.of(post.getPostImages().get(index).getFilePath());
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(ErrorCode.NOT_FOUND_IMAGE_BY_INDEX + index);
        }
    }

    public String getPostImageFileNameOnIndexByPostId(Long postId, int index) {
        Post post = getPostByPostId(postId);
        try {
            return post.getPostImages().get(index).getFilePath().split("post/")[1];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(ErrorCode.NOT_FOUND_IMAGE_BY_INDEX + index);
        }
    }

    @Transactional
    public void likePost(Long postId) {
        Member member = memberService.getMeById();
        Post post = getPostByPostId(postId);
        member.appendLikedPost(post);
    }

    @Transactional
    public void unlikePost(Long postId) {
        Member member = memberService.getMeById();
        Post post = getPostByPostId(postId);
        member.subtractedLikedPost(post);
    }

    @Transactional
    public void deletePostByPostId(Long postId) {
        Post post = getPostByPostId(postId);
        post.removeAllMemberLikeConnection();
        postRepository.delete(post);
    }

    public void validateAuthor(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Post post = getPostByPostId(postId);
        if (!currentMemberId.equals(post.getAuthor().getId())) {
            throw new NotAuthorException(currentMemberId);
        }
    }
}
