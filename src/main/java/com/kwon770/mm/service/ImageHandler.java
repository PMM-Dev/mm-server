package com.kwon770.mm.service;

import com.kwon770.mm.domain.post.Post;
import com.kwon770.mm.domain.post.PostImage;
import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.RestaurantImage;
import com.kwon770.mm.domain.restaurant.review.RestaurantReview;
import com.kwon770.mm.domain.restaurant.review.RestaurantReviewImage;
import com.kwon770.mm.exception.ImageIOException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class ImageHandler {

    private static String ABSOULTE_PATH = new File("").getAbsolutePath() + "/";
    private static String RESTAURANT_IMAGES_PATH = ABSOULTE_PATH + "images/restaurant/";
    private static String REVIEW_IMAGES_PATH = ABSOULTE_PATH + "images/review/";
    private static String POST_IMAGES_PATH = ABSOULTE_PATH + "images/post/";

    public void downloadImage(MultipartFile image, String filePath) {
        try {
            File file = new File(filePath);
            image.transferTo(file);
        } catch (IOException e) {
            throw new ImageIOException(e);
        }
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

    public RestaurantImage parseRestaurantImage(Restaurant restaurant, MultipartFile image) {
        validateSavingPath(RESTAURANT_IMAGES_PATH);
        validatePictureExtension(image.getContentType());

        String fileName = System.nanoTime() + getFileExtension(image.getContentType());
        return RestaurantImage.builder()
                .originalFileName(image.getOriginalFilename())
                .filePath(RESTAURANT_IMAGES_PATH + fileName)
                .fileSize(image.getSize())
                .restaurant(restaurant)
                .build();
    }

    public PostImage parsePostImage(Post post, MultipartFile image) {
        validateSavingPath(POST_IMAGES_PATH);
        validatePictureExtension(image.getContentType());

        String fileName = System.nanoTime() + getFileExtension(image.getContentType());
        return PostImage.builder()
                .originalFileName(image.getOriginalFilename())
                .filePath(POST_IMAGES_PATH + fileName)
                .fileSize(image.getSize())
                .post(post)
                .build();
    }

    public RestaurantReviewImage parseReviewImage(RestaurantReview restaurantReview, MultipartFile image) {
        validateSavingPath(REVIEW_IMAGES_PATH);
        validatePictureExtension(image.getContentType());

        String fileName = System.nanoTime() + getFileExtension(image.getContentType());
        return RestaurantReviewImage.builder()
                .originalFileName(image.getOriginalFilename())
                .filePath(REVIEW_IMAGES_PATH + fileName)
                .fileSize(image.getSize())
                .restaurantReview(restaurantReview)
                .build();
    }
}
