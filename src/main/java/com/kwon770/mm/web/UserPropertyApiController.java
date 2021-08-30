package com.kwon770.mm.web;

import com.kwon770.mm.service.UserPropertyService;
import com.kwon770.mm.web.dto.LikedRestaurantDto;
import com.kwon770.mm.web.dto.UserTitleSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserPropertyApiController {

    private final UserPropertyService userPropertyService;

    @PostMapping("/api/title")
    public Long saveTitle(@RequestBody UserTitleSaveDto userTitleSaveDto) {
        return userPropertyService.saveTitle(userTitleSaveDto);
    }

    @DeleteMapping("/api/title/{title}")
    public void deleteTitle(@PathVariable String title) { userPropertyService.deleteTitle(title); }

    @PutMapping("/api/user/{email}/title/{title}")
    public void appendTitle(@PathVariable String email, @PathVariable String title) {
        userPropertyService.appendTitle(email, title);
    }

    @DeleteMapping("/api/user/{email}/title/{title}")
    public void subtractTitle(@PathVariable String email, @PathVariable String title) {
        userPropertyService.subtractTitle(email, title);
    }

    @GetMapping("/api/user/{email}/like")
    public List<LikedRestaurantDto> getLikedRestaurantList(@PathVariable String email) {
        return userPropertyService.getLikedRestaurantList(email);
    }

    @PutMapping("/api/user/{email}/like/{restaurantId}")
    public void appendLikedRestaurant(@PathVariable String email, @PathVariable Long restaurantId) {
        userPropertyService.appendLikedRestaurant(email, restaurantId);
    }

    @DeleteMapping("/api/user/{email}/like/{restaurantId}")
    public void subtractLikedRestaurant(@PathVariable String email, @PathVariable Long restaurantId) {
        userPropertyService.subtractedLikedRestaurant(email, restaurantId);
    }
}
