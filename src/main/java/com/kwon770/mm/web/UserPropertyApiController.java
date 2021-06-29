package com.kwon770.mm.web;

import com.kwon770.mm.service.RestaurantPropertyService;
import com.kwon770.mm.service.UserPropertyService;
import com.kwon770.mm.web.dto.UserTitleSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserPropertyApiController {

    private final UserPropertyService userPropertyService;

    @PostMapping("/api/user/title")
    public Long saveTitle(@RequestBody UserTitleSaveDto userTitleSaveDto) {
        return userPropertyService.saveTitle(userTitleSaveDto);
    }

    @DeleteMapping("/api/user/title/{title}")
    public void deleteTitle(@PathVariable String title) { userPropertyService.deleteTitle(title); }

    @PutMapping("/api/user/{email}/title/{title}")
    public void appendTitle(@PathVariable String email, @PathVariable String title) {
        userPropertyService.appendTitle(email, title);
    }

    @DeleteMapping("/api/user/{email}/title/{title}")
    public void subtractTitle(@PathVariable String email, @PathVariable String title) {
        userPropertyService.subtractTitle(email, title);
    }
}
