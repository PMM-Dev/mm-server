package com.kwon770.mm.web;

import com.kwon770.mm.exception.ImageIOException;
import com.kwon770.mm.service.ImageService;
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
public class PictureApiController {

    private final ImageService imageService;

    @PostMapping("/restaurant/{restaurantId}/picture")
    public ResponseEntity<Void> uploadRestaurantPicture(@PathVariable Long restaurantId, @RequestParam("image") MultipartFile picture) {
        try {
            imageService.uploadRestaurantPicture(restaurantId, picture);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (IOException e) {
            throw new ImageIOException(e);
        }
    }

    @PostMapping("/restaurant/{restaurantId}/thumbnail")
    public ResponseEntity<Void> uploadRestaurantThumbnail(@PathVariable Long restaurantId, @RequestParam("image") MultipartFile thumbnail) {
        try {
            imageService.uploadRestaurantThumbnail(restaurantId, thumbnail);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (IOException e) {
            throw new ImageIOException(e);
        }
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

    @GetMapping("/restaurant/{restaurantId}/picture")
    public ResponseEntity<Void> getRestaurantPicture(HttpServletResponse response, @PathVariable Long restaurantId) {
        Optional<String> picturePath = imageService.getRestaurantPicturePath(restaurantId);
        return outputImage(response, picturePath);
    }

    @GetMapping("/restaurant/{restaurantId}/thumbnail")
    public ResponseEntity<Void> getRestaurantThumbnail(HttpServletResponse response, @PathVariable Long restaurantId) {
        Optional<String> thumbnailPath = imageService.getRestaurantThumbnail(restaurantId);
        return outputImage(response, thumbnailPath);
    }

    @DeleteMapping("/restaurant/{restaurantId}/picture")
    public ResponseEntity<Void> deleteRestaurantPicture(@PathVariable Long restaurantId) {
        imageService.deleteRestaurantPicture(restaurantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/restaurant/{restaurantId}/thumbnail")
    public ResponseEntity<Void> deleteRestaurantThumbnail(@PathVariable Long restaurantId) {
        imageService.deleteRestaurantThumbnail(restaurantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
