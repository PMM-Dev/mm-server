package com.kwon770.mm.web.dto.post;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostRequest {
    private PostRequestDto postRequestDto;
    private MultipartFile[] postImages;
}
