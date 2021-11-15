package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.RestaurantImage;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class ImageHandler {

    private static String absolutePath = new File("").getAbsolutePath() + "/";
    private static String restaurantPicturePath = absolutePath + "images/restaurant/picture/";
    private static String restaurantThumbnailPath = absolutePath + "images/restaurant/thumbnail/";

    public void downloadImage(MultipartFile image, String filePath) throws IOException {
        File file = new File(filePath);
        image.transferTo(file);
    }

    private void validateSavingPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void validatePictureExtension(String contentType) {
        if (!contentType.contains("image/jpg") &&
            !contentType.contains("image/jpeg") &&
            !contentType.contains("image/gif") &&
            !contentType.contains("image/png")) {
            throw new IllegalArgumentException("올바르지 않은 이미지입니다 : " + contentType);
        }
    }

    private String getFileExtension(String contentType) {
        return "." + contentType.split("/")[1];
    }

    public RestaurantImage parseRestaurantPicture(MultipartFile picture) {
        return parseRestaurantImage(picture, restaurantPicturePath);
    }

    public RestaurantImage parseRestaurantThumbnail(MultipartFile thumbnail) {
        return parseRestaurantImage(thumbnail, restaurantThumbnailPath);
    }

    private RestaurantImage parseRestaurantImage(MultipartFile image, String path) {
        validateSavingPath(path);
        validatePictureExtension(image.getContentType());

        String fileName = System.nanoTime() + getFileExtension(image.getContentType());
        return RestaurantImage.builder()
                .originalFileName(image.getOriginalFilename())
                .filePath(path + fileName)
                .fileSize(image.getSize())
                .build();
    }
}
