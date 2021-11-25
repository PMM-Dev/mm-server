package com.kwon770.mm.web;

import com.kwon770.mm.dto.post.PostElementDto;
import com.kwon770.mm.dto.restaurant.RestaurantElementDto;
import com.kwon770.mm.service.SearchService;
import com.kwon770.mm.dto.member.MemberSearchDto;
import com.kwon770.mm.dto.restaurant.RestaurantSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchApiController {

    private final SearchService searchService;

    @GetMapping("/search/restaurant")
    public ResponseEntity<List<RestaurantElementDto>> searchRestaurantByName(@RequestParam(value = "keyword") String keyword) {
        return new ResponseEntity<>(searchService.searchRestaurantByKeyword(keyword), HttpStatus.OK);
    }

    @GetMapping("/search/member")
    public ResponseEntity<List<MemberSearchDto>> searchMemberByNameOrEmail(@RequestParam(value = "keyword") String keyword) {
        return new ResponseEntity<>(searchService.searchMemberByKeyword(keyword), HttpStatus.OK);
    }

    @GetMapping("/search/post")
    public ResponseEntity<List<PostElementDto>> searchPostByTitle(@RequestParam(value = "keyword") String keyword) {
        return new ResponseEntity<>(searchService.searchPostByKeyword(keyword), HttpStatus.OK);
    }
}
