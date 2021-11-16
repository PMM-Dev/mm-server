package com.kwon770.mm.service;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.RestaurantImage;
import com.kwon770.mm.domain.restaurant.RestaurantImageRepository;
import com.kwon770.mm.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageHandler imageHandler;

    private final RestaurantService restaurantService;
    private final RestaurantImageRepository restaurantImageRepository;

    @Transactional
    public void uploadRestaurantImages(Long restaurantId, MultipartFile picture, MultipartFile thumbnail) throws IOException {
        RestaurantImage restaurantPicture = imageHandler.parseRestaurantPicture(picture);
        RestaurantImage restaurantThumbnail = imageHandler.parseRestaurantThumbnail(thumbnail);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        restaurant.setRestaurantImages(restaurantPicture, restaurantThumbnail);

        imageHandler.downloadImage(picture, restaurantPicture.getFilePath());
        imageHandler.downloadImage(thumbnail, restaurantThumbnail.getFilePath());
        restaurantImageRepository.save(restaurantPicture);
        restaurantImageRepository.save(restaurantThumbnail);
    }

    public Optional<String> getRestaurantPicturePath(Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Optional<RestaurantImage> restaurantPicture = restaurant.getRestaurantPicture();
        if (restaurantPicture.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(restaurantPicture.get().getFilePath());
    }

    public Optional<String> getRestaurantThumbnail(Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Optional<RestaurantImage> restaurantThumbnail = restaurant.getRestaurantThumbnail();
        if (restaurantThumbnail.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(restaurantThumbnail.get().getFilePath());
    }

    public void deleteRestaurantImages(Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Optional<RestaurantImage> restaurantPicture = restaurant.getRestaurantPicture();
        restaurantPicture.ifPresent(restaurantImageRepository::delete);

        Optional<RestaurantImage> restaurantThumbnail = restaurant.getRestaurantThumbnail();
        restaurantThumbnail.ifPresent(restaurantImageRepository::delete);
    }
}
