package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.RestaurantImage;
import com.kwon770.mm.domain.restaurant.RestaurantImageRepository;
import com.kwon770.mm.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageHandler imageHandler;

    private final RestaurantService restaurantService;
    private final RestaurantImageRepository restaurantImageRepository;

    public void uploadRestaurantPicture(Long restaurantId, MultipartFile picture) throws IOException {
        RestaurantImage restaurantPicture = imageHandler.parseRestaurantPicture(picture);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        restaurantPicture.setRestaurant(restaurant);

        imageHandler.downloadImage(picture, restaurantPicture.getFilePath());
        restaurantImageRepository.save(restaurantPicture);
    }

    public void uploadRestaurantThumbnail(Long restaurantId, MultipartFile thumbnail) throws IOException {
        RestaurantImage restaurantThumbnail = imageHandler.parseRestaurantThumbnail(thumbnail);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        restaurantThumbnail.setRestaurant(restaurant);

        imageHandler.downloadImage(thumbnail, restaurantThumbnail.getFilePath());
        restaurantImageRepository.save(restaurantThumbnail);
    }

    public Optional<String> getRestaurantPicturePath(Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        RestaurantImage restaurantPicture = restaurant.getRestaurantPicture();
        if (restaurantPicture == null) {
            return Optional.empty();
        }

        return Optional.of(restaurantPicture.getFilePath());
    }

    public Optional<String> getRestaurantThumbnail(Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        RestaurantImage restaurantThumbnail = restaurant.getRestaurantThumbnail();
        if (restaurantThumbnail == null) {
            return Optional.empty();
        }

        return Optional.of(restaurantThumbnail.getFilePath());
    }

    public void deleteRestaurantPicture(Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        RestaurantImage restaurantPicture = restaurant.getRestaurantPicture();
        if (restaurantPicture == null) {
            throw new IllegalArgumentException(ErrorCode.NO_IMAGE_BY_RESTAURANTID + restaurantId);
        }

        restaurantImageRepository.delete(restaurantPicture);
    }

    public void deleteRestaurantThumbnail(Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        RestaurantImage restaurantThumbnail = restaurant.getRestaurantThumbnail();
        if (restaurantThumbnail == null) {
            throw new IllegalArgumentException(ErrorCode.NO_IMAGE_BY_RESTAURANTID + restaurantId);
        }

        restaurantImageRepository.delete(restaurantThumbnail);
    }
}
