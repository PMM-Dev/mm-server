package com.kwon770.mm.service.restaurant;

import com.kwon770.mm.domain.restaurant.Restaurant;
import com.kwon770.mm.domain.restaurant.RestaurantImage;
import com.kwon770.mm.domain.restaurant.RestaurantImageRepository;
import com.kwon770.mm.exception.ErrorCode;
import com.kwon770.mm.service.ImageHandler;
import com.kwon770.mm.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RestaurantImageService {

    private final ImageHandler imageHandler;

    private final RestaurantService restaurantService;
    private final RestaurantImageRepository restaurantImageRepository;

    @Transactional
    public void uploadRestaurantImages(Long restaurantId, List<MultipartFile> images) {
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantId(restaurantId);
        List<RestaurantImage> restaurantImages = generateRestaurantImages(restaurant, images);

        restaurantImageRepository.saveAll(restaurantImages);
    }

    private List<RestaurantImage> generateRestaurantImages(Restaurant restaurant, List<MultipartFile> images) {
        return images.stream().map(image -> {
            RestaurantImage restaurantImage = imageHandler.parseRestaurantImage(restaurant, image);
            imageHandler.downloadImage(image, restaurantImage.getFilePath());
            return restaurantImage;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void updateRestaurantImage(Long restaurantId, List<MultipartFile> images) {
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantId(restaurantId);

        List<RestaurantImage> oldRestaurantImages = restaurant.getRestaurantImages();
        for (RestaurantImage oldImage : oldRestaurantImages) {
            CommonUtil.removeImageFromServer(oldImage.getFilePath());
            restaurantImageRepository.delete(oldImage);
        }

        List<RestaurantImage> newRestaurantImages = generateRestaurantImages(restaurant, images);
        restaurantImageRepository.saveAll(newRestaurantImages);
    }

    public Optional<String> getRestaurantImagePathOnIndexByRestaurantId(Long restaurantId, int index) {
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantId(restaurantId);
        try {
            return Optional.of(restaurant.getRestaurantImages().get(index).getFilePath());
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(ErrorCode.NOT_FOUND_IMAGE_BY_INDEX + index);
        }
    }
}
