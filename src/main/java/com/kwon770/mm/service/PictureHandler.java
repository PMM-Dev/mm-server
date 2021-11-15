package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.RestaurantPicture;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class PictureHandler {

    private static String absolutePath = new File("").getAbsolutePath() + "/";
    private static String restaurantPicturePath = absolutePath + "images/restaurant/picture";
    private static String restaurantThumbnailPath = absolutePath + "images/restaurant/thumbnail";

    public void downloadPicture(MultipartFile multipartFile, String filePath) throws IOException {
        String absolutePath = new File("").getAbsolutePath() + "/";
        File file = new File(absolutePath + filePath);
        multipartFile.transferTo(file);
    }

    private void validateSavingPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void validatePictureExtension(String contentType) {
        if (!contentType.contains("image/jpg")
            && !contentType.contains("image/jpeg")
            && !contentType.contains("image/gif")
            && !contentType.contains("image/png")) {
            throw new IllegalArgumentException("올바르지 않은 이미지입니다 : " + contentType);
        }
    }

    private String getFileExtension(String contentType) {
        return "." + contentType.split("/")[1];
    }

    public RestaurantPicture parseRestaurantPicture(MultipartFile multipartFile) {
        validateSavingPath(restaurantPicturePath);
        validatePictureExtension(multipartFile.getContentType());

        String fileExtension = getFileExtension(multipartFile.getContentType());
        String fileName = System.nanoTime() + fileExtension;
        return RestaurantPicture.builder()
                .originalFileName(multipartFile.getOriginalFilename())
                .filePath(restaurantPicturePath + fileName)
                .fileSize(multipartFile.getSize())
                .build();
    }
}