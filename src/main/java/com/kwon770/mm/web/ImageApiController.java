package com.kwon770.mm.web;

import com.kwon770.mm.exception.ImageIOException;
import com.kwon770.mm.exception.SystemIOException;
import com.kwon770.mm.service.post.PostService;
import com.kwon770.mm.service.restaurant.RestaurantImageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    private void outputImage(HttpServletResponse response, Optional<String> imagePath) {
        if (imagePath.isEmpty()) {
            return;
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

    @GetMapping("/image/restaurant/{restaurantId}/picture")
    public void getRestaurantPicture(HttpServletResponse response, @PathVariable Long restaurantId) {
        Optional<String> picturePath = restaurantImageService.getRestaurantPicturePath(restaurantId);
        outputImage(response, picturePath);
    }

    @GetMapping("/image/restaurant/{restaurantId}/thumbnail")
    public void getRestaurantThumbnail(HttpServletResponse response, @PathVariable Long restaurantId) {
        Optional<String> thumbnailPath = restaurantImageService.getRestaurantThumbnail(restaurantId);
        outputImage(response, thumbnailPath);
    }

    @DeleteMapping("/image/restaurant/{restaurantId}/image")
    public ResponseEntity<Void> deleteRestaurantPicture(@PathVariable Long restaurantId) {
        restaurantImageService.deleteRestaurantImages(restaurantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/image/post/{postId}/{index}")
    public void getPostImageOnIndexByPostId(HttpServletResponse response, @PathVariable Long postId, @PathVariable int index) {
        Optional<String> imagePath = postService.getPostImagePathOnIndexByPostId(postId, index);
        outputImage(response, imagePath);
    }
}
