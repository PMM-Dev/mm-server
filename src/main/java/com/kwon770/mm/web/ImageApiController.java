package com.kwon770.mm.web;

import com.kwon770.mm.exception.ImageIOException;
import com.kwon770.mm.service.post.PostService;
import com.kwon770.mm.service.restaurant.RestaurantImageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final RestaurantImageService restaurantImageService;
    private final PostService postService;

    @PostMapping("/image/restaurant/{restaurantId}/image")
    public ResponseEntity<Void> uploadRestaurantImages(@PathVariable Long restaurantId, @RequestParam("picture") MultipartFile picture, @RequestParam("thumbnail") MultipartFile thumbnail) {
        restaurantImageService.uploadRestaurantImages(restaurantId, picture, thumbnail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<Void> outputImage(HttpServletResponse response, Optional<String> imagePath) {
        try {
            if (imagePath.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            byte[] pictureBytes = FileUtils.readFileToByteArray(new File(imagePath.get()));
            response.getOutputStream().write(pictureBytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            throw new ImageIOException(e);
        }
    }

    @GetMapping("/image/restaurant/{restaurantId}/picture")
    public ResponseEntity<Void> getRestaurantPicture(HttpServletResponse response, @PathVariable Long restaurantId) {
        Optional<String> picturePath = restaurantImageService.getRestaurantPicturePath(restaurantId);
        return outputImage(response, picturePath);
    }

    @GetMapping("/image/restaurant/{restaurantId}/thumbnail")
    public ResponseEntity<Void> getRestaurantThumbnail(HttpServletResponse response, @PathVariable Long restaurantId) {
        Optional<String> thumbnailPath = restaurantImageService.getRestaurantThumbnail(restaurantId);
        return outputImage(response, thumbnailPath);
    }

    @DeleteMapping("/image/restaurant/{restaurantId}/image")
    public ResponseEntity<Void> deleteRestaurantPicture(@PathVariable Long restaurantId) {
        restaurantImageService.deleteRestaurantImages(restaurantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/image/post/{postId}/{index}")
    public ResponseEntity<Void> getPostImageOnIndexByPostId(HttpServletResponse response, @PathVariable Long postId, @PathVariable int index) {
        Optional<String> imagePath = postService.getPostImagePathOnIndexByPostId(postId, index);
        return outputImage(response, imagePath);
    }
}
