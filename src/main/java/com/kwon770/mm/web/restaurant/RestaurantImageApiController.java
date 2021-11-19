package com.kwon770.mm.web.restaurant;

import com.kwon770.mm.exception.ImageIOException;
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
public class RestaurantImageApiController {

    private final RestaurantImageService restaurantImageService;

    @PostMapping("/restaurant/{restaurantId}/image")
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

    @GetMapping("/restaurant/{restaurantId}/picture")
    public ResponseEntity<Void> getRestaurantPicture(HttpServletResponse response, @PathVariable Long restaurantId) {
        Optional<String> picturePath = restaurantImageService.getRestaurantPicturePath(restaurantId);
        return outputImage(response, picturePath);
    }

    @GetMapping("/restaurant/{restaurantId}/thumbnail")
    public ResponseEntity<Void> getRestaurantThumbnail(HttpServletResponse response, @PathVariable Long restaurantId) {
        Optional<String> thumbnailPath = restaurantImageService.getRestaurantThumbnail(restaurantId);
        return outputImage(response, thumbnailPath);
    }

    @DeleteMapping("/restaurant/{restaurantId}/image")
    public ResponseEntity<Void> deleteRestaurantPicture(@PathVariable Long restaurantId) {
        restaurantImageService.deleteRestaurantImages(restaurantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
