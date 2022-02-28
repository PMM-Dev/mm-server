package com.kwon770.mm.web;

import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.exception.SystemIOException;
import com.kwon770.mm.service.post.PostService;
import com.kwon770.mm.service.restaurant.RestaurantImageService;
import com.kwon770.mm.service.restaurant.review.RestaurantReviewService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final RestaurantImageService restaurantImageService;
    private final PostService postService;
    private final RestaurantReviewService restaurantReviewService;

    @PostMapping("/image/restaurant/{restaurantId}/image")
    public ResponseEntity<Void> uploadRestaurantImages(@PathVariable Long restaurantId, @RequestParam("images") List<MultipartFile> images) {
        restaurantImageService.uploadRestaurantImages(restaurantId, images);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/image/restaurant/{restaurantId}/image")
    public ResponseEntity<Void> updateRestaurantImage(@PathVariable Long restaurantId, @RequestParam("images") List<MultipartFile> images) {
        restaurantImageService.updateRestaurantImage(restaurantId, images);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/image/restaurant/{restaurantId}/image/{index}")
    public void getRestaurantImageOnIndexByRestaurantId(HttpServletResponse response, @PathVariable Long restaurantId, @PathVariable int index) {
        Optional<String> imagePath = restaurantImageService.getRestaurantImagePathOnIndexByRestaurantId(restaurantId, index);
        outputImage(response, imagePath);
    }

    @GetMapping("/image/restaurant/review/{reviewId}")
    public void getReviewImageByReviewId(HttpServletResponse response, @PathVariable Long reviewId) {
        Optional<String> imagePath = restaurantReviewService.getReviewImageByReviewId(reviewId);
        outputImage(response, imagePath);
    }

    @GetMapping("/image/restaurant/review/{reviewId}/fileName")
    public ResponseEntity<String> getReviewImageFileNameByReviewId(@PathVariable Long reviewId) {
        String fileName = restaurantReviewService.getReviewImageFileNameByReviewId(reviewId);

        return new ResponseEntity<>(fileName, HttpStatus.OK);
    }

    @GetMapping("/image/post/{postId}/{index}")
    public void getPostImageOnIndexByPostId(HttpServletResponse response, @PathVariable Long postId, @PathVariable int index) {
        Optional<String> imagePath = postService.getPostImagePathOnIndexByPostId(postId, index);
        outputImage(response, imagePath);
    }

    @GetMapping("/image/post/{postId}/{index}/fileName")
    public ResponseEntity<String> getPostImageFileNameOnIndexByPostId(@PathVariable Long postId, @PathVariable int index) {
        String fileName = postService.getPostImageFileNameOnIndexByPostId(postId, index);

        return new ResponseEntity<>(fileName, HttpStatus.OK);
    }

    private void outputImage(HttpServletResponse response, Optional<String> imagePath) {
        if (imagePath.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NOT_FOUND_IMAGE);
        }

        File file = new File(imagePath.get());
        if (!file.isFile()) {
            return;
        }

        FileInputStream fis = null;
        BufferedInputStream in = null;
        ByteArrayOutputStream bStream = null;
        try {
            fis = new FileInputStream(file);
            in = new BufferedInputStream(fis);
            bStream = new ByteArrayOutputStream();
            int imgByte;
            while ((imgByte = in.read()) != -1) {
                bStream.write(imgByte);
            }

            String type = "";
            String ext = FilenameUtils.getExtension(file.getName());
            if (!ext.isEmpty()) {
                if (ext.equalsIgnoreCase("jpg")) {
                    type = "image/jpeg";
                } else {
                    type = "image/" + ext.toLowerCase();
                }
            }

            response.setHeader("Content-Type", type);
            response.setContentLength(bStream.size());
            bStream.writeTo(response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            throw new SystemIOException(e);
        } finally {
            try {
                if (bStream != null) {
                    bStream.close();
                }
                if (in != null) {
                    in.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                throw new SystemIOException(e);
            }
        }
    }
}
