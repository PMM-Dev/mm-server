package com.kwon770.mm.service.post;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.post.Post;
import com.kwon770.mm.domain.post.PostImage;
import com.kwon770.mm.domain.post.PostImageRepository;
import com.kwon770.mm.domain.post.PostRepository;
import com.kwon770.mm.service.ImageHandler;
import com.kwon770.mm.service.member.MemberService;
import com.kwon770.mm.util.SecurityUtil;
import com.kwon770.mm.web.dto.post.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

        createPictures(post, images);
        postRepository.save(post);
        return post.getId();
    }

    private void createPictures(Post post, List<MultipartFile> images) {
        for (MultipartFile image : images) {
            PostImage postImage = imageHandler.parsePostImage(post, image);
            imageHandler.downloadImage(image, postImage.getFilePath());
            postImageRepository.save(postImage);
        }
    }
}
